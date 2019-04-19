package ojt.crscube.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * Created by taesu at : 2019-04-17
 *
 * Audit 기능 활성화 설정
 *
 * @author taesu
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EnableAuditConfiguration {
    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Optional.of(-1L);
    }
}