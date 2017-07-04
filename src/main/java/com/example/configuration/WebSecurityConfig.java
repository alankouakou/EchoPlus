package com.example.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;

	@Bean
	public SpringTemplateEngine templateEngine(
	SpringResourceTemplateResolver templateResolver) {
	SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	templateEngine.setTemplateResolver(templateResolver);
	templateEngine.addDialect(new LayoutDialect());
	templateEngine.addDialect(new SpringSecurityDialect());
	return templateEngine;
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select username,password,true from User where username=?")
		.authoritiesByUsernameQuery("select u.username, r.name from user u, role r where u.role_id=r.id and u.username=?")
		.passwordEncoder(new BCryptPasswordEncoder());
		// En phase de test
		//auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN")
		//.and()
		//.withUser("user").password("password").roles("USER");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/webjars/**","/users/new","/img/**").permitAll()
		//.antMatchers("/login*").permitAll()
		.antMatchers("/**").authenticated()
		.and()
		.formLogin().loginPage("/login")
		.permitAll()
		.and()
		.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/login?logout")
		.and()
		.exceptionHandling().accessDeniedPage("/403")
		;
		
		
	}

}
