package ojt.crscube.i18n.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor @AllArgsConstructor @Builder @Getter
@Entity
@Table(name = "MST_LABEL", 
       uniqueConstraints = {@UniqueConstraint(columnNames = {"I18N_KEY", "LOCALE"})}
       )
@SequenceGenerator(name = "SEQ_LABEL", sequenceName = "SEQ_LABEL")
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
    private ApplicationLocale locale;

    @Column(name = "VALUE", nullable = false)
    private String value;
}
