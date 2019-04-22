package ojt.crscube.member.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.domain.exception.EntityNotFoundException;
import ojt.crscube.member.domain.model.Member;
import ojt.crscube.member.domain.repository.MemberRepository;
import ojt.crscube.member.interfaces.dto.PasswordDto.PasswordChangeRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by taesu at : 2019-04-22
 *
 * 비밀번호 변경 서비스
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Component @RequiredArgsConstructor
public class PasswordChangeService {
    private final MemberRepository memberRepository;

    @Transactional
    public void changePassword(Long memberKey, PasswordChangeRequest request) {
        request.requestValidation();
        Member member = this.memberRepository.findById(memberKey)
                                             .orElseThrow(EntityNotFoundException::new);

        member.changePassword(request.getOriginPassword(), request.getNewPassword(), request.getReason());
        this.memberRepository.save(member);
    }
}
