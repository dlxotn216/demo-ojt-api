package ojt.crscube.member.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.member.domain.repository.MemberRepository;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberCreateRequest;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberCreateResponse;
import org.springframework.stereotype.Component;

import static ojt.crscube.member.domain.model.Member.createNewMember;
import static ojt.crscube.member.interfaces.dto.MemberDto.MemberCreateResponse.from;

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
public class MemberCreateService {

    private final MemberRepository memberRepository;

    public MemberCreateResponse createMember(MemberCreateRequest request) {
        request.requestValidation();

        if (this.memberRepository.findById(request.getId()).isPresent()) {
            throw new IllegalArgumentException("MEMBER.DUPLICATED_ID");
        }

        return from(this.memberRepository.save(
                createNewMember(request.getId(), request.getName(), request.getPassword())));
    }
}
