package com.example.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.converters.GroupConverter;
import com.example.converters.PersonConverter;
import com.example.converters.UserConverter;
import com.example.service.GroupService;
import com.example.service.PersonService;
import com.example.service.UserService;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	

	@Bean
	public UserService userService() {
		return new UserService();
	}
	
	@Bean
	public PersonService personService(){
		return new PersonService();
	}
	
	@Bean
	public GroupService groupService(){
		return new GroupService();
	}
	
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/employees/**");
		//super.addInterceptors(registry);
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new PersonConverter(personService()));
		registry.addConverter(new GroupConverter(groupService()));
		registry.addConverter(new UserConverter(userService()));
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		resolver.setMaxPageSize(10);
		resolver.setOneIndexedParameters(true);
		argumentResolvers.add(resolver);
		super.addArgumentResolvers(argumentResolvers);
	}
}
