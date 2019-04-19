package ojt.crscube.member.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ojt.crscube.base.domain.model.EntityBase;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ojt.crscube.base.utils.EncryptionUtils.encode;
import static ojt.crscube.base.utils.EncryptionUtils.matches;

/**
 * Created by taesu at : 2019-04-19
 *
 * 사용자 비밀번호
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor @AllArgsConstructor @Getter
@Entity @Table(name = "MST_PASSWORD")
@SequenceGenerator(name = "SEQ_PASSWORD", sequenceName = "SEQ_PASSWORD")
@Audited(withModifiedFlag = true) @EntityListeners(value = {AuditingEntityListener.class})
public class Password {
    private static final String FORMAT_INVALID = "PASSWORD.INVALID";
    /**
     * 최소 8자리에 숫자, 문자, 특수문자 각각 1개 이상 포함
     */
    private static final String FORMAT_REGX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PASSWORD")
    @Column(name = "PASSWORD_KEY", unique = true, nullable = false, updatable = false)
    private Long key;

    @Column(name = "PASSWORD", nullable = false)
    private String encryptedPassword;

    @OneToOne
    @JoinColumn(name = "PASSWORD_KEY")
    private Member member;

    @Embedded
    private EntityBase entityBase = new EntityBase();

    static Password createNewPassword(Member member, String passwordSource) {
        passwordValidation(passwordSource);

        Password password = new Password();
        password.member = member;
        password.encryptedPassword = encode(passwordSource);

        return password;
    }

    private static void passwordValidation(String passwordSource) {
        if (StringUtils.isEmpty(passwordSource)) {
            throw new IllegalArgumentException(FORMAT_INVALID);
        }
        
        Matcher matcher = Pattern.compile(FORMAT_REGX).matcher(passwordSource);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(FORMAT_INVALID);
        }
    }

    public boolean isMatchedPassword(String password) {
        return matches(password, this.encryptedPassword);
    }
}
