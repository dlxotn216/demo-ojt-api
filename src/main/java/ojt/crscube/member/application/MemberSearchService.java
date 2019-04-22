package ojt.crscube.member.application;

import lombok.RequiredArgsConstructor;
import ojt.crscube.base.domain.exception.EntityNotFoundException;
import ojt.crscube.member.domain.model.Member;
import ojt.crscube.member.domain.repository.MemberRepository;
import ojt.crscube.member.interfaces.dto.MemberDto.MemberSearchResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static ojt.crscube.member.interfaces.dto.MemberDto.MemberSearchResponse.from;

/**
 * Created by taesu at : 2019-04-22
 *
 * 사용자 조회 Service
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Component @RequiredArgsConstructor
public class MemberSearchService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberSearchResponse searchOrThrow(String id) {
        return from(searchOrThrowById(id));
    }

    public Member searchOrThrowById(String id) {
        return this.memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
