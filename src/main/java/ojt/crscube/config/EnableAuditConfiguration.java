package ojt.crscube.config;

import ojt.crscube.member.domain.model.Member;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

import static ojt.crscube.auth.AuthenticationContextHolder.getAuthentication;

/**
 * Created by taesu at : 2019-04-17
 * <p>
 * Audit 기능 활성화 설정
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class EnableAuditConfiguration {

    @Configuration
    @EnableJpaAuditing(auditorAwareRef = "auditorAware")
    @Profile("test")
    static class TestAuditorAwareConfig {
        @Bean
        public AuditorAware<Long> auditorAware() {
            return () -> Optional.of(-1L);
        }
    }

    @Configuration
    @EnableJpaAuditing(auditorAwareRef = "auditorAware")
    @Profile("!test")
    static class ServiceAuditorAwareConfig {
        @Bean
        public AuditorAware<Long> auditorAware() {
            return () -> {
                Member authentication = getAuthentication();
                if (authentication == null) {
                    return Optional.of(-1L);
                }
                return Optional.of(authentication.getKey());
            };
        }
    }
}