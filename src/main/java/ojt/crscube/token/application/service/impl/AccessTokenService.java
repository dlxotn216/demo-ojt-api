package ojt.crscube.token.application.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ojt.crscube.member.domain.model.Member;
import ojt.crscube.member.domain.repository.MemberRepository;
import ojt.crscube.token.application.service.TokenService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

import static ojt.crscube.base.utils.Messages.ACCESS_TOKEN_EXPIRED;
import static ojt.crscube.base.utils.Messages.ACCESS_TOKEN_INVALID;

/**
 * Created by taesu on 2019-04-19.
 */
@Service("AccessTokenService") @Slf4j @RequiredArgsConstructor
public class AccessTokenService implements TokenService<Member> {

    private final MemberRepository memberRepository;

    /**
     * Token 의 암복호화를 위한 Secret
     * HMAC 알고리즘을 사용하기 때문에 반드시 256 bit 이어야 함
     * 추후 KMS(Key management Solution 을 통해 API 통신 방식으로 변경 가능)
     */
    private static final byte[] SECRETS;

    private static final long DEFAULT_EXPIRATION_DURATION_HOUR;

    /**
     * 토큰의 발행처 정보로 발급 기관은 일치해야 위변조 방지를 할 수 있다
     */
    private static final String ISSUER;

    static {
        SECRETS = "CRSCUBE_32BYTE_SECRET_STRING_KEY".substring(0, 32).getBytes();
        DEFAULT_EXPIRATION_DURATION_HOUR = 10L;
        ISSUER = "https://www.crscube.io";
    }

    @Override
    public String generateTokenValue(Member source) {
        ZonedDateTime expirationTime
                = ZonedDateTime.now(ZoneOffset.UTC).plusHours(DEFAULT_EXPIRATION_DURATION_HOUR);

        return generateTokenValue(source, expirationTime);
    }

    /**
     * expirationTime 이 null 인 경우 만료시간이 없는 토큰을 생성 할 수 있다 (개발용 등)
     *
     * @param source         Token source
     * @param expirationTime 만료 시간
     * @return API 인증토큰
     */
    @Override
    public String generateTokenValue(Member source, ZonedDateTime expirationTime) {
        ZonedDateTime current = ZonedDateTime.now(ZoneOffset.UTC);

        JWTClaimsSet.Builder builder = source.getClaimedBuilder()
                .subject(source.getId())                                //AccessToken 의 발행 대상
                .issuer(ISSUER)                                             //발행처
                .issueTime(Date.from(current.toInstant()));                 //발행 시간

        if (expirationTime != null) {
            builder.expirationTime(Date.from(expirationTime.toInstant()));  //만료 시간
        }

        JWTClaimsSet claimsSet = builder.build();

        return getEncryptedTokenValue(claimsSet);

    }

    /**
     * 전달받은 Payload 를 암호화 하여 토큰을 생성한다
     *
     * @param claimsSet Payload
     * @return 암호화 된 Token value
     * @throws RuntimeException 암호화 중 발생(KeyLengthException, JOSEException)
     */
    private String getEncryptedTokenValue(JWTClaimsSet claimsSet) {
        JWSSigner signer;
        try {
            signer = new MACSigner(SECRETS);
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);

            JWEObject jweObject = new JWEObject(new JWEHeader.Builder(JWEAlgorithm.DIR, EncryptionMethod.A256GCM)
                    .contentType("JWT") // Nested JWT 명시
                    .build(),
                    new Payload(signedJWT));

            jweObject.encrypt(new DirectEncrypter(SECRETS));
            return jweObject.serialize();

        } catch (KeyLengthException e) {
            log.error("Key Length exception ", e);
            throw new UnsupportedOperationException("Key Length Exception", e);
        } catch (JOSEException e) {
            log.error("JOSEE Exception", e);
            throw new UnsupportedOperationException("JOSEE Exception", e);
        }
    }

    @Override
    public Member parseTokenValue(String tokenValue) {
        try {
            JWEObject jweObject = JWEObject.parse(tokenValue);

            jweObject.decrypt(new DirectDecrypter(SECRETS));
            SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();

            JWSVerifier verifier = new MACVerifier(SECRETS);
            signedJWT.verify(verifier);

            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            validationClaimSet(jwtClaimsSet);

            return getConvertedSource(jwtClaimsSet);

        } catch (ParseException e) {
            log.error("ParseException ", e);
            throw new IllegalStateException(ACCESS_TOKEN_INVALID);
        } catch (KeyLengthException e) {
            log.error("KeyLengthException", e);
            throw new UnsupportedOperationException("KeyLengthException", e);
        } catch (JOSEException e) {
            log.error("JOSEE Exception", e);
            throw new UnsupportedOperationException("JOSEE Exception", e);
        }
    }

    private Member getConvertedSource(JWTClaimsSet jwtClaimsSet) {
        try {
            return this.memberRepository.findById((String) jwtClaimsSet.getClaim("id"))
                    .orElseThrow(() -> new IllegalStateException(ACCESS_TOKEN_INVALID));
        } catch (Exception e) {
            throw new IllegalStateException(ACCESS_TOKEN_INVALID);
        }
    }

    /**
     * ClaimSet 의 변환을 수행하기 전 수행 가능 한 유효성 검사를 진행한다.
     *
     * @param jwtClaimsSet 복호화 된 ClaimSet
     */
    private void validationClaimSet(JWTClaimsSet jwtClaimsSet) {
        if (!Objects.equals(jwtClaimsSet.getIssuer(), ISSUER)) {
            log.error("Token Issuer[" + jwtClaimsSet.getIssuer() + "] is not equals [" + ISSUER + "]");
            throw new IllegalStateException(ACCESS_TOKEN_INVALID);
        }

        Date expirationTimeInClaimSet = jwtClaimsSet.getExpirationTime();
        if (expirationTimeInClaimSet == null) {
            return;
        }

        ZonedDateTime current = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime expirationTime
                = ZonedDateTime.ofInstant(jwtClaimsSet.getExpirationTime().toInstant(), ZoneOffset.UTC);
        if (expirationTime.isBefore(current)) {
            log.error("Expiration[" + expirationTime + "] time is before than current[" + current + "]");
            throw new IllegalStateException(ACCESS_TOKEN_EXPIRED);
        }

    }
}
