package com.rktech.product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication extends SpringBootServletInitializer {
	/*
	Note: To create/packaging your application as war we have to extends
	our main class with SpringBootServletInitializer and override configure method as below.

	SpringBoot by default support packing as .jar file. So we don't need configure anything.
	**/
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProductServiceApplication.class);
	}

	public static void main(String[] args) {

		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
