package ojt.crscube.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ojt.crscube.locale.domain.model.ApplicationLocale;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by taesu on 2019-04-20.
 */
@Component
@Getter @Setter
@ConfigurationProperties(prefix = "labels")
public class I18nConfigurationProperties {
    private List<I18nConfig> i18ns = new ArrayList<>();

    @Data
    public static class I18nConfig implements Serializable {
        private String i18nId;
        private List<I18nLabelConfig> values;
    }

    @Data
    public static class I18nLabelConfig implements Serializable {
        private String locale;
        private String value;

        public Locale getLocale() {
            return ApplicationLocale.valueOf(this.locale.toUpperCase()).getLocale();
        }
    }
}
