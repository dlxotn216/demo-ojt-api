package ojt.crscube.board.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ojt.crscube.base.domain.model.EntityBase;
import ojt.crscube.member.domain.model.Member;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @CreatedBy
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER", updatable = false)
    private Member writer;

    @Builder.Default
    @Embedded
    private EntityBase entityBase = new EntityBase();

    public LocalDateTime getUpdatedDateTime() {
        return this.entityBase.getUpdateDateTime();
    }

    public Boolean isWriter(Long memberKey) {
        return Objects.equals(this.getWriter().getKey(), memberKey);
    }

    public Board update(String subject, String content, String reason) {
        this.subject = subject;
        this.content = content;
        this.entityBase.setReason(reason);
        return this;
    }

    public Board delete(String reason) {
        this.entityBase.setDeleted(true);
        this.entityBase.setReason(reason);
        return this;
    }

    public Board restore(String reason) {
        this.entityBase.setDeleted(false);
        this.entityBase.setReason(reason);
        return this;
    }
}
