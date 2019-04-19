package ojt.crscube.runner;

import lombok.RequiredArgsConstructor;
import ojt.crscube.i18n.application.I18nCreateService;
import ojt.crscube.i18n.interfaces.dto.I18nDto;
import ojt.crscube.locale.domain.model.ApplicationLocale;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;

import static ojt.crscube.base.utils.Messages.*;

/**
 * Created by taesu on 2019-04-20.
 */
@Component @RequiredArgsConstructor
public class ApplicationInitializer implements ApplicationRunner {
    private final I18nCreateService i18nCreateService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        this.i18nCreateService.createI18n(new I18nDto.I18nCreateRequest(REQUIRED_PARAMETER,
                new HashMap<Locale, String>() {
                    {
                        put(ApplicationLocale.EN.getLocale(), "Missing required parameters.");
                        put(ApplicationLocale.KO.getLocale(), "필수 파라미터가 누락되었습니다.");
                    }
                }));

        this.i18nCreateService.createI18n(new I18nDto.I18nCreateRequest(PASSWORD_NOT_MATCHED,
                new HashMap<Locale, String>() {
                    {
                        put(ApplicationLocale.EN.getLocale(), "Password do not matched.");
                        put(ApplicationLocale.KO.getLocale(), "비밀번호가일치하지 않습니다.");
                    }
                }));

        this.i18nCreateService.createI18n(new I18nDto.I18nCreateRequest(LOGIN_INVALID_MEMBER,
                new HashMap<Locale, String>() {
                    {
                        put(ApplicationLocale.EN.getLocale(), "Invalid Member or Member password");
                        put(ApplicationLocale.KO.getLocale(), "유효하지 않은 사용자이거나 비밀번호입니다.");
                    }
                }));

        this.i18nCreateService.createI18n(new I18nDto.I18nCreateRequest(ACCESS_TOKEN_INVALID,
                new HashMap<Locale, String>() {
                    {
                        put(ApplicationLocale.EN.getLocale(), "Invalid Access token");
                        put(ApplicationLocale.KO.getLocale(), "유효하지 않은 Access token입니다.");
                    }
                }));

        this.i18nCreateService.createI18n(new I18nDto.I18nCreateRequest(ACCESS_TOKEN_EXPIRED,
                new HashMap<Locale, String>() {
                    {
                        put(ApplicationLocale.EN.getLocale(), "Access token has been expired");
                        put(ApplicationLocale.KO.getLocale(), "Access token이 만료되었습니다.");
                    }
                }));

        this.i18nCreateService.createI18n(new I18nDto.I18nCreateRequest(MEMBER_DUPLICATED_ID,
                new HashMap<Locale, String>() {
                    {
                        put(ApplicationLocale.EN.getLocale(), "Duplicated Member ID");
                        put(ApplicationLocale.KO.getLocale(), "중복된 사용자 아이디입니다.");
                    }
                }));

        this.i18nCreateService.createI18n(new I18nDto.I18nCreateRequest(EXCEPTION_UNSUPPORTED_LOCALE,
                new HashMap<Locale, String>() {
                    {
                        put(ApplicationLocale.EN.getLocale(), "Locale not supported.");
                        put(ApplicationLocale.KO.getLocale(), "지원하지 않는 Locale 입니다.");
                    }
                }));

        this.i18nCreateService.createI18n(new I18nDto.I18nCreateRequest(MESSAGE_NOT_FOUND,
                new HashMap<Locale, String>() {
                    {
                        put(ApplicationLocale.EN.getLocale(), "Message not found,");
                        put(ApplicationLocale.KO.getLocale(), "메시지가 존재하지 않습니다.");
                    }
                }));

        this.i18nCreateService.createI18n(new I18nDto.I18nCreateRequest(RESPONSE_SUCCESS,
                new HashMap<Locale, String>() {
                    {
                        put(ApplicationLocale.EN.getLocale(), "Request was successfully proceed.");
                        put(ApplicationLocale.KO.getLocale(), "요청이 정상적으로 처리되었습니다.");
                    }
                }));
    }
}
