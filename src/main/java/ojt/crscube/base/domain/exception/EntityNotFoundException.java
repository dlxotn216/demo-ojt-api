package ojt.crscube.base.domain.exception;

import org.springframework.core.NestedRuntimeException;

import static ojt.crscube.base.utils.Messages.ENTITY_NOT_FOUND;

/**
 * Created by taesu at : 2019-04-22
 *
 * Entity 조회 시 존재하지 않는 경우 발생하는 Exception
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public class EntityNotFoundException extends NestedRuntimeException {
    /**
     * Construct a {@code NestedRuntimeException} with the specified detail message.
     */
    public EntityNotFoundException() {
        super(ENTITY_NOT_FOUND);
    }
}
