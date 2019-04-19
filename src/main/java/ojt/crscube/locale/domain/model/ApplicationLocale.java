package ojt.crscube.locale.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ojt.crscube.i18n.domain.exception.UnSupportedLocaleException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.function.Function;

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
    EN(Locale.ENGLISH, "English", 0),
    KO(Locale.KOREAN, "한국어", 1);

    private Locale locale;
    private String name;
    private Integer order;

    public static ApplicationLocale getDefaultLocale() {
        return ApplicationLocale.EN;
    }

    public static final Function<Locale, ApplicationLocale> localeToApplicationLocale = requestLocale -> {
        try {
            return ApplicationLocale.valueOf(requestLocale.toString().toUpperCase());
        } catch (Exception e) {
            throw new UnSupportedLocaleException(requestLocale.toString());
        }
    };

    public static ApplicationLocale getCurrentLocale() {
        try {
            return localeToApplicationLocale.apply(LocaleContextHolder.getLocale());
        } catch (UnSupportedLocaleException e) {
            return getDefaultLocale();
        }
    }
}
