package ojt.crscube.member.domain.repository;

import ojt.crscube.member.domain.model.MemberPassword;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by taesu at : 2019-04-19
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public interface PasswordRepository extends JpaRepository<MemberPassword, Long> {
}
