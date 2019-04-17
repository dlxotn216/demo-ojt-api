package ojt.crscube.locale.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ojt.crscube.locale.domain.model.ApplicationLocale;

import java.util.Locale;

/**
 * Created by taesu at : 2019-04-17
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
public final class LocaleDto {

    public static LocaleSearchResponse toResponse(ApplicationLocale applicationLocale) {
        return new LocaleSearchResponse(applicationLocale.getLocale(), applicationLocale.getName());
    }

    @Data
    @NoArgsConstructor @AllArgsConstructor
    public static class LocaleSearchResponse {
        private Locale locale;
        private String name;
    }
}
