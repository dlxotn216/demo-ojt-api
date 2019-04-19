package ojt.crscube.member.interfaces.controller;

import lombok.RequiredArgsConstructor;
import ojt.crscube.member.domain.model.Member;
import ojt.crscube.member.domain.repository.MemberRepository;
import ojt.crscube.member.domain.repository.PasswordRepository;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberLoginRequest;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberLoginResponse;
import org.springframework.stereotype.Component;

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
    private final PasswordRepository passwordRepository;

    public MemberLoginResponse login(MemberLoginRequest request) {
        request.requestValidation();

        Member member = this.memberRepository.findById(request.getId())
                                       .orElseThrow(() -> new IllegalArgumentException("LOGIN.INVALID_MEMBER"));
        
        if (!this.passwordRepository.findByMember(member).isMatchedPassword(request.getPassword())) {
            throw new IllegalArgumentException("LOGIN.INVALID_MEMBER");
        }


        return null;
    }
}
