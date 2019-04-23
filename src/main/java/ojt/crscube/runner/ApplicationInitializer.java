package ojt.crscube.runner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ojt.crscube.config.I18nConfigurationProperties;
import ojt.crscube.config.I18nConfigurationProperties.I18nLabelConfig;
import ojt.crscube.i18n.application.I18nCreateService;
import ojt.crscube.i18n.interfaces.dto.I18nDto.I18nCreateRequest;
import ojt.crscube.member.domain.model.Member;
import ojt.crscube.member.domain.repository.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toMap;

/**
 * Created by taesu on 2019-04-20.
 */
@Component @RequiredArgsConstructor @Getter @Setter @Profile({"init"})
public class ApplicationInitializer implements ApplicationRunner {
    private final I18nCreateService i18nCreateService;
    private final I18nConfigurationProperties i18NConfigurationProperties;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Member admin = Member.builder().id("admin").name("System Admin").build();
        admin.setMemberPassword("admin123!@#");
        this.memberRepository.save(admin);

        this.i18NConfigurationProperties
                .getI18ns()
                .stream()
                .map(this::getI18nCreateRequest)
                .forEach(this.i18nCreateService::createI18n);
    }

    private I18nCreateRequest getI18nCreateRequest(I18nConfigurationProperties.I18nConfig i18nConfig) {
        return new I18nCreateRequest(i18nConfig.getI18nId(),
                                     i18nConfig.getValues()
                                               .stream()
                                               .collect(toMap(I18nLabelConfig::getLocale, I18nLabelConfig::getValue)));
    }
}
