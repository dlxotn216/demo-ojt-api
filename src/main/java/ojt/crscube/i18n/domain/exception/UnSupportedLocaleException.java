package ojt.crscube.i18n.domain.exception;

import lombok.Getter;
import org.springframework.core.NestedRuntimeException;

/**
 * Created by taesu at : 2019-04-17
 *
 * 여기에 UnSupportedLocaleException 클래스에 대한 설명을 기술해주세요
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Getter
public class UnSupportedLocaleException extends NestedRuntimeException {
    private static final String MESSAGE = "EXCEPTION.UNSUPPORTED_LOCALE";
    private final String locale;

    public UnSupportedLocaleException(String locale) {
        super(MESSAGE);
        this.locale = locale;
    }

    public UnSupportedLocaleException(String locale,
                                      Throwable cause) {
        super(MESSAGE, cause);
        this.locale = locale;
    }
}
