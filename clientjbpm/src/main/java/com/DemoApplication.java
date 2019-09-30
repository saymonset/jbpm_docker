package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages={"com"})
public class DemoApplication {

	/**public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}**/
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}




}
