package com.example.demo.customAnnotation;

public @interface ToMyCustomSecurityService {
	
	public String authorization() default  "";
	
	public String securityEndpointService() default "";
	

}
