package ojt.crscube.config;

import lombok.RequiredArgsConstructor;
import ojt.crscube.interceptor.AuthorizationInterceptor;
import ojt.crscube.locale.domain.model.ApplicationLocale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static ojt.crscube.locale.domain.model.ApplicationLocale.getDefaultLocale;
import static ojt.crscube.locale.domain.model.ApplicationLocale.values;

/**
 * Web application configuration
 * Created by taesu on 2019-04-19.
 */
@Profile("service") @Configuration @RequiredArgsConstructor
public class WebApplicationConfiguration implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/**");
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(getDefaultLocale().getLocale());
        acceptHeaderLocaleResolver.setSupportedLocales(
                of(values()).map(ApplicationLocale::getLocale).collect(toList()));
        return acceptHeaderLocaleResolver;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(),
                                HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name())
                .allowCredentials(false)
                .maxAge(3600);
    }
}