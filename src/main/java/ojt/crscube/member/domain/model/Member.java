package ojt.crscube.member.domain.model;

import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ojt.crscube.base.domain.model.EntityBase;
import ojt.crscube.token.domain.TokenSource;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static ojt.crscube.base.utils.Messages.PASSWORD_NOT_MATCHED;
import static ojt.crscube.member.domain.model.MemberPassword.createNewPassword;

/**
 * Created by taesu at : 2019-04-19
 * <p>
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
public class Member implements TokenSource{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_MEMBER")
    @Column(name = "MEMBER_KEY", unique = true, nullable = false, updatable = false)
    private Long key;

    @Column(name = "MEMBER_ID", unique = true, nullable = false)
    private String id;

    @Column(name = "MEMBER_NAME", nullable = false)
    private String name;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MemberPassword memberPassword;

    @Builder.Default
    @Embedded
    private EntityBase entityBase = new EntityBase();

    public void updatePassword(String originPassword, String newPassword) {
        if (!this.memberPassword.isMatchedPassword(originPassword)) {
            throw new IllegalArgumentException(PASSWORD_NOT_MATCHED);
        }

        this.setMemberPassword(newPassword);
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = createNewPassword(this, memberPassword);
    }

    public static Member createNewMember(String id, String name, String password) {
        Member member = Member.builder().id(id).name(name).build();
        member.setMemberPassword(password);

        return member;
    }

    @Override
    public JWTClaimsSet.Builder getClaimedBuilder() {
        return new JWTClaimsSet.Builder()
                .claim("id", getId());
    }
}
