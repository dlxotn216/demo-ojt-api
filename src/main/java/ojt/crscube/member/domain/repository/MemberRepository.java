package ojt.crscube.member.domain.repository;

import ojt.crscube.member.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Created by taesu at : 2019-04-19
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(@Param("id") String id);
}
