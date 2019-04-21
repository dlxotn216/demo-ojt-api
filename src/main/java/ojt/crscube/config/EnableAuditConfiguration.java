package ojt.crscube.config;

import lombok.RequiredArgsConstructor;
import ojt.crscube.member.domain.model.Member;
import ojt.crscube.member.domain.repository.MemberRepository;
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

    @RequiredArgsConstructor
    @Configuration
    @EnableJpaAuditing(auditorAwareRef = "auditorAware")
    @Profile("test")
    static class TestAuditorAwareConfig {
        private final MemberRepository memberRepository;

        @Bean
        public AuditorAware<Member> auditorAware() {
            return () ->
                    this.memberRepository.findById(1L);
        }
    }

    @RequiredArgsConstructor
    @Configuration
    @EnableJpaAuditing(auditorAwareRef = "auditorAware")
    @Profile("!test")
    static class ServiceAuditorAwareConfig {
        private final MemberRepository memberRepository;

        @Bean
        public AuditorAware<Member> auditorAware() {
            return () -> {
                Member authentication = getAuthentication();
                if (authentication == null) {
                    return this.memberRepository.findById(1L);
                }
                return Optional.of(authentication);
            };
        }
    }
}