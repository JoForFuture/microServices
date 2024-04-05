package com.example.demo.tosecurityservice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ToMyCustomSecurityService {
	
	public String authorization() default  "";
	
	public String securityEndpointService() default "";

}
