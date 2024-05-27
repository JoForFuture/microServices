package com.example.demo.tosecurityservice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * remember annotate main class with @EnableAspectJAutoProxy
 * @apiNote authorization : token
 * @apiNote securityEndpointService : end-point for security-service(USER or ADMIN) 
 * {@value default end-point value will cover all roles}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ToMyCustomSecurityService {
	
	/**
	 * The Json Web Token 
	 * default empty String
	 * @return emptyString or insert value
	 */
	public String authorization() default  "";
	
	/**
	 * @apiNote endpoint of security service for authenticate all services by only one security-service. By default is "http://security-service/securityControl/accessPoint" and not make differences between different roles.
	 * @return
	 */
	public String securityEndpointService() default "http://security-service/securityControl/accessPoint";

}
