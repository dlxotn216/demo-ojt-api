package ojt.crscube.runner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ojt.crscube.config.I18nConfigurationProperties;
import ojt.crscube.config.I18nConfigurationProperties.I18nLabelConfig;
import ojt.crscube.i18n.application.I18nCreateService;
import ojt.crscube.i18n.interfaces.dto.I18nDto.I18nCreateRequest;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toMap;

/**
 * Created by taesu on 2019-04-20.
 */
@Component @RequiredArgsConstructor @Getter @Setter
public class ApplicationInitializer implements ApplicationRunner {
    private final I18nCreateService i18nCreateService;
    private final I18nConfigurationProperties i18NConfigurationProperties;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        this.i18NConfigurationProperties.getI18ns()
                .stream()
                .map(i18nConfig -> new I18nCreateRequest(i18nConfig.getI18nId(),
                        i18nConfig.getValues().stream().collect(toMap(I18nLabelConfig::getLocale, I18nLabelConfig::getValue))))
                .forEach(this.i18nCreateService::createI18n);
    }
}
