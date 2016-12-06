package com.example.configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(securedEnabled=true)
public class MethodsSecurityConfig extends GlobalMethodSecurityConfiguration {

}
