package ojt.crscube.base.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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

    @CreatedDate
    @Column(name = "CREATE_DATETIME", updatable = false, nullable = false)
    private LocalDateTime createDateTime;

    @CreatedBy
    @Column(name = "CREATED_BY", updatable = false, nullable = false)
    private Long createdBy;

    @LastModifiedDate
    @Column(name = "UPDATE_DATETIME")
    private LocalDateTime updateDateTime;

    @LastModifiedBy
    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "DESCRIPTION")
    private String description = "-";

    @Column(name = "REASON")
    private String reason = "-";

}
