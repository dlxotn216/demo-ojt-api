package ojt.crscube.locale.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ojt.crscube.i18n.domain.exception.UnSupportedLocaleException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import static java.util.stream.Stream.of;

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
    EN(Locale.ENGLISH, Arrays.asList(Locale.ENGLISH), "English", 0),
    KO(Locale.KOREAN, Arrays.asList(Locale.KOREA, Locale.KOREAN), "한국어", 1);

    private Locale locale;
    private List<Locale> alias;
    private String name;
    private Integer order;

    public boolean containsAlias(Locale target) {
        return this.alias.contains(target);
    }

    public static ApplicationLocale getDefaultLocale() {
        return ApplicationLocale.EN;
    }

    public static ApplicationLocale getCurrentLocale() {
        try {
            return CONVERTER.apply(LocaleContextHolder.getLocale());
        } catch (UnSupportedLocaleException e) {
            return getDefaultLocale();
        }
    }

    public static final Function<Locale, ApplicationLocale> CONVERTER
            = requestLocale -> of(ApplicationLocale.values())
                                .filter(appLocale -> appLocale.containsAlias(requestLocale))
                                .findAny()
                                .orElseThrow(() -> new UnSupportedLocaleException(requestLocale.toString()));
}
