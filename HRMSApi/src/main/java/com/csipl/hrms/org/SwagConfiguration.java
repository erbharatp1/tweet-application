
package com.csipl.hrms.org;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration

@EnableSwagger2 
@ComponentScan(basePackages = "com.csipl.hrms")

@EnableAutoConfiguration
public class SwagConfiguration {

	@Bean
	public Docket oauthApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.csipl.hrms.employee")).paths(PathSelectors.any())

				.apis(RequestHandlerSelectors.any())

				.build().apiInfo(metaData());
	}

	private ApiInfo metaData() {

		return new ApiInfoBuilder().title("HRMS").description("HRMS servics").termsOfServiceUrl("")
				.contact(new Contact("csipl", "", "info@gmail.com")).license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0").version("2.0").build();

	}

}
