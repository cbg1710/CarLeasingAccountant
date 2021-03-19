package de.chris.apps.cars;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Car-Leasing accountant")
                .description("This gives you an overview over " +
                        "your Car Leasing contract. Especially about how much distance in kilometers you " +
                        "allowed to drive")
                .license("MIT License")
                .licenseUrl("")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("cgb1710", "https://github.com/cbg1710", ""))
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.chris.apps.cars"))
                .paths(PathSelectors.any())
                .build()
        .apiInfo(apiInfo());
    }
}
