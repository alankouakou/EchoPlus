package com.example;

import java.io.IOException;
import java.util.Locale;

import javax.persistence.criteria.Predicate.BooleanOperator;
import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.example.model.Account;
import com.example.model.User;

@SpringBootApplication
@ComponentScan({"com.example"})
@PropertySource("classpath:config.properties")
public class MybootApplication {
	
	@Autowired
	private Environment env;

	@Bean
	public Account getAccount(){
		Account account = new Account(env.getProperty("infobip.login"),env.getProperty("infobip.password"));
		return account;
	}

	@Bean(name="multipartResolver") 
    public StandardServletMultipartResolver getResolver() throws IOException{
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
         
        return resolver;
    }
	

	
//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("messages");
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }
//  
    
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement( LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
        return multipartConfigElement;
    }
 
    private static final String LOCATION = "/static/upload/"; // Temporary location where files will be stored
 
    private static final long MAX_FILE_SIZE = 10485760; // 10MB : Max file size.
                                                        // Beyond that size spring will throw exception.
    private static final long MAX_REQUEST_SIZE = 20971520; // 20MB : Total request size containing Multi part.
     
    private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after which files will be written to dis
    
    
	public static void main(String[] args) {
		SpringApplication.run(MybootApplication.class, args);
	}
}
