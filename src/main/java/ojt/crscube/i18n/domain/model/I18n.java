package ojt.crscube.i18n.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ojt.crscube.base.domain.model.EntityBase;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taesu at : 2019-04-12
 *
 * 다국어 유형에 따른 식별 Domain
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor @AllArgsConstructor @Builder @Getter
@Entity @Table(name = "MST_I18N")
@SequenceGenerator(name = "SEQ_I18N", sequenceName = "SEQ_I18N")
@Audited(withModifiedFlag = true) @EntityListeners(value = {AuditingEntityListener.class})
public class I18n {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_I18N")
    @Column(name = "I18N_KEY", unique = true, nullable = false, updatable = false)
    private Long key;

    @Column(name = "I18N_ID", unique = true, nullable = false, updatable = false)
    private String id;

    @Builder.Default
    @OneToMany(mappedBy = "i18n",
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Label> labels = new ArrayList<>();
    
    @Embedded
    private EntityBase entityBase;
}
