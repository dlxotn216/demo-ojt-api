package ojt.crscube.base.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ojt.crscube.member.domain.model.Member;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by taesu at : 2019-04-17
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Embeddable
@Getter @NoArgsConstructor @AllArgsConstructor
public class EntityBase {

    @LastModifiedDate
    @Column(name = "UPDATE_DATETIME")
    private LocalDateTime updateDateTime;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    private Member updatedBy;

    @Column(name = "DELETED")
    private Boolean deleted = false;

    @Column(name = "DESCRIPTION")
    private String description = "-";

    @Column(name = "REASON")
    private String reason = "-";

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setReason(String reason) {
        this.reason = StringUtils.isEmpty(reason)
                ? "-"
                : reason;
    }
}
