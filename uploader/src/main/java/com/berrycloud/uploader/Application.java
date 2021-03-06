package com.berrycloud.uploader;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();

		factory.setMaxFileSize("4096Mb");
		factory.setMaxRequestSize("4096Mb");

		return factory.createMultipartConfig();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}