package ojt.crscube.i18n.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ojt.crscube.base.domain.model.EntityBase;
import ojt.crscube.locale.domain.model.ApplicationLocale;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by taesu at : 2019-04-12
 *
 * Locale에 따른 다국어 라벨을 표현하는 Domain
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor @AllArgsConstructor @Getter
@Entity
@Table(name = "MST_LABEL",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"I18N_KEY", "LOCALE"})}
)
@SequenceGenerator(name = "SEQ_LABEL", sequenceName = "SEQ_LABEL")
@Audited(withModifiedFlag = true) @EntityListeners(value = {AuditingEntityListener.class})
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LABEL")
    @Column(name = "LABEL_KEY", unique = true, nullable = false, updatable = false)
    private Long key;

    @Column(name = "LABEL_ID", unique = true, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "I18N_KEY")
    private I18n i18n;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "LOCALE", nullable = false)
    private ApplicationLocale applicationLocale;

    @Column(name = "VALUE", nullable = false)
    private String value;

    @Embedded
    private EntityBase entityBase = new EntityBase();

    public static Label from(I18n i18n, ApplicationLocale locale, String value) {
        Label label = new Label();
        label.id = i18n.getId() + "_" + locale.getLocale();
        label.i18n = i18n;
        label.applicationLocale = locale;
        label.value = value;
        return label;
    }
}
