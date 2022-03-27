package com.liveproject.oauth2.resource.health.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerApiConfiguration {

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.OAS_30)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.liveproject.oauth2.resource.health"))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("HealthX Resource API")
                .description("An API to collect and process patient health-related parameters")
                .license("MANNING")
                .licenseUrl("http://unlicense.org")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("","", ""))
                .build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("SimplySend Procurement API")
                        .description("An API to collect and process patient health-related parameters")
                        .termsOfService("")
                        .version("1.0.0")
                        .license(new License()
                                .name("MANNING")
                                .url("http://unlicense.org"))
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .email("")));
    }
}
