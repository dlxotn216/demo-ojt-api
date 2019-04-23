package ojt.crscube;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@SpringBootApplication
public class DemoApiApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(DemoApiApplication.class)
                .properties("spring.config.location="
                                    + "classpath:/application.yml"
                                    + ", file:./config/application-variable.yml")
                .run(args);

    }

}
