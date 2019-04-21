package ojt.crscube.board.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ojt.crscube.base.domain.model.EntityBase;
import ojt.crscube.member.domain.model.Member;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by taesu on 2019-04-21.
 *
 * Board Entity
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor @AllArgsConstructor @Builder @Getter
@Entity @Table(name = "MST_BOARD")
@SequenceGenerator(name = "SEQ_BOARD", sequenceName = "SEQ_BOARD")
@Audited(withModifiedFlag = true) @EntityListeners(value = {AuditingEntityListener.class})
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_BOARD")
    @Column(name = "BOARD_KEY", unique = true, nullable = false, updatable = false)
    private Long key;

    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    @Column(name = "CONTENT", nullable = false)
    @Lob
    private String content;

    @Builder.Default
    @Embedded
    private EntityBase entityBase = new EntityBase();

    public LocalDateTime getUpdatedDateTime() {
        return this.entityBase.getUpdateDateTime();
    }

    public Member getUpdatedBy() {
        return this.entityBase.getUpdatedBy();
    }

}
