package ojt.crscube.member.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.member.domain.model.Member;
import ojt.crscube.member.domain.repository.MemberRepository;
import ojt.crscube.member.domain.repository.PasswordRepository;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberLoginRequest;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberLoginResponse;
import ojt.crscube.token.application.service.TokenService;
import org.springframework.stereotype.Component;

import static ojt.crscube.base.utils.Messages.LOGIN_INVALID_MEMBER;
import static ojt.crscube.member.interfaces.dto.MemberDto.MemberLoginResponse.from;

/**
 * Created by taesu at : 2019-04-19
 *
 * 사용자 로그인 서비스
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Component @RequiredArgsConstructor
public class MemberLoginService {
    private final MemberRepository memberRepository;
    private final TokenService<Member> tokenService;

    public MemberLoginResponse login(MemberLoginRequest request) {
        request.requestValidation();

        Member member = this.memberRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException(LOGIN_INVALID_MEMBER));

        if (!member.isMatchedPassword(request.getPassword())) {
            throw new IllegalArgumentException(LOGIN_INVALID_MEMBER);
        }

        return from(member, tokenService.generateTokenValue(member));
    }
}
