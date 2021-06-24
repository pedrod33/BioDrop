package pt.deliveries.business_initiative.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket apiDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pt.deliveries.business_initiative"))
                .paths(PathSelectors.regex("/.*"))
                .build();
    }
    ApiInfo apiInfo() {
        return new ApiInfo(
                "BioDrop",
                "BioDrop",
                "0.1",
                "Terms of service",
                new Contact("BioDrop", "www.biodrop.com", "biodrop@gmail.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }
}