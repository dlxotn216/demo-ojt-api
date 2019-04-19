package ojt.crscube.member.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ojt.crscube.base.domain.model.EntityBase;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static ojt.crscube.member.domain.model.Password.createNewPassword;

/**
 * Created by taesu at : 2019-04-19
 *
 * System 사용자
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor @AllArgsConstructor @Builder @Getter
@Entity @Table(name = "MST_MEMBER")
@SequenceGenerator(name = "SEQ_MEMBER", sequenceName = "SEQ_MEMBER")
@Audited(withModifiedFlag = true) @EntityListeners(value = {AuditingEntityListener.class})
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_MEMBER")
    @Column(name = "MEMBER_KEY", unique = true, nullable = false, updatable = false)
    private Long key;

    @Column(name = "MEMBER_ID", unique = true, nullable = false)
    private String id;

    @Column(name = "MEMBER_NAME", nullable = false)
    private String name;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Password password;

    @Builder.Default
    @Embedded
    private EntityBase entityBase = new EntityBase();

    public void updatePassword(String originPassword, String newPassword) {
        if (!this.password.isMatchedPassword(originPassword)) {
            throw new IllegalArgumentException("PASSWORD.NOT_MATCHED");
        }

        this.setPassword(newPassword);
    }

    private void setPassword(String password) {
        this.password = createNewPassword(this, password);
    }

    public static Member createNewMember(String id, String name, String password) {
        Member member = Member.builder().id(id).name(name).build();
        member.setPassword(password);

        return member;
    }
}
