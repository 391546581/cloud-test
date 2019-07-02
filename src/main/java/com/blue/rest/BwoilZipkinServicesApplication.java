package com.blue.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(scanBasePackages = {"com.blue.rest.api.**"})
@EnableFeignClients(basePackages = {"com.blue.rest.feign"})
@EnableEurekaClient
@EnableSwagger2
@ServletComponentScan
public class BwoilZipkinServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BwoilZipkinServicesApplication.class, args);
	}

}
