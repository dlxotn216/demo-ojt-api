package ojt.crscube.i18n.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

/**
 * Created by taesu at : 2019-04-12
 *
 * Application에서 지원하는 Locale에 대한 Enum이다.
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor @Getter
public enum ApplicationLocale {
    ENGLISH(Locale.ENGLISH),
    KOREAN(Locale.KOREAN);

    private Locale locale;
}
