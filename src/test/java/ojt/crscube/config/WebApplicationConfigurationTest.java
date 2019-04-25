package ojt.crscube.config;

import ojt.crscube.interceptor.AuthorizationInterceptor;
import ojt.crscube.locale.interfaces.dto.LocaleDto;
import ojt.crscube.member.domain.model.Member;
import ojt.crscube.token.application.service.TokenService;
import org.junit.Test;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.lang.reflect.Constructor;
import java.time.ZonedDateTime;
import java.util.Locale;

/**
 * Created by Lee Tae Su on 2019-04-25.
 */
public class WebApplicationConfigurationTest {
    @Test
    public void 생성자_테스트() throws Exception {
        for (Constructor<?> constructor : WebApplicationConfiguration.class.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            Object o = constructor.newInstance(new AuthorizationInterceptor(new TokenService<Member>() {
                @Override
                public String generateTokenValue(Member source) {
                    return null;
                }

                @Override
                public String generateTokenValue(Member source, ZonedDateTime expirationTime) {
                    return null;
                }

                @Override
                public Member parseTokenValue(String tokenValue) {
                    return null;
                }
            }));

            ((WebApplicationConfiguration) o).addInterceptors(new InterceptorRegistry());
            ((WebApplicationConfiguration) o).localeResolver();
        }
    }
}