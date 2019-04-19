package ojt.crscube.member.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.member.domain.repository.MemberRepository;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberSignUpRequest;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberSignUpResponse;
import org.springframework.stereotype.Component;

import static ojt.crscube.base.utils.Messages.MEMBER_DUPLICATED_ID;
import static ojt.crscube.member.domain.model.Member.createNewMember;
import static ojt.crscube.member.interfaces.dto.MemberDto.MemberSignUpResponse.from;

/**
 * Created by taesu at : 2019-04-19
 *
 * 사용자 생성 서비스
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Component @RequiredArgsConstructor
public class MemberSignUpService {

    private final MemberRepository memberRepository;

    public MemberSignUpResponse createMember(MemberSignUpRequest request) {
        request.requestValidation();

        if (this.memberRepository.findById(request.getId()).isPresent()) {
            throw new IllegalArgumentException(MEMBER_DUPLICATED_ID);
        }

        return from(this.memberRepository.save(
                createNewMember(request.getId(), request.getName(), request.getPassword())));
    }
}
