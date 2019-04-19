package ojt.crscube.token.domain;

import com.nimbusds.jwt.JWTClaimsSet.Builder;

/**
 * Created by taesu on 2019-04-19.
 */
public interface TokenSource {
    /**
     * 토큰의 타입 T에 따라 구현체에서 사용할 필드로 구성 된
     * Builder 을 반환한다
     *
     * @return 토큰의 타입에 따라 build 된 ClaimSet Builder
     */
    Builder getClaimedBuilder();
}
