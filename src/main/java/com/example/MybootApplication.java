package com.example;

import java.io.IOException;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.example.model.Account;

@SpringBootApplication
@ComponentScan({ "com.example" })
@PropertySource("classpath:config.properties")
public class MybootApplication {

	// Temporary location where files will be stored
	private static final String LOCATION = "/static/upload/";

	// 10MB : Max file size.
	private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

	// Beyond that size spring will throw exception.
	// 20MB : Total request size containing Multipart.
	private static final long MAX_REQUEST_SIZE = 20971520;

	// Size threshold after which files will be written to disk
	private static final int FILE_SIZE_THRESHOLD = 0;

	@Autowired
	private Environment env;

    public CommonsMultipartResolver multipartResolver() throws IOException{
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
         
        //Set the maximum allowed size (in bytes) for each individual file.
        multipartResolver.setMaxUploadSizePerFile(MAX_FILE_SIZE);
         
        //You may also set other available properties.
         
        return multipartResolver;
    }
	
	@Bean(name="multipartResolver")
	public StandardServletMultipartResolver getResolver() throws IOException {
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
		return resolver;
	}

	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(LOCATION, MAX_FILE_SIZE,
				MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
		return multipartConfigElement;
	}

	@Bean
	public Account getAccount() {
		Account account = new Account(env.getProperty("infobip.login"), env.getProperty("infobip.password"));
		return account;
	}

	// @Bean
	// public MessageSource messageSource() {
	// ResourceBundleMessageSource messageSource = new
	// ResourceBundleMessageSource();
	// messageSource.setBasename("messages");
	// messageSource.setDefaultEncoding("UTF-8");
	// return messageSource;
	// }
	//

	public static void main(String[] args) {
		SpringApplication.run(MybootApplication.class, args);
	}
}
