package com.example.demo.Controllers.utilities;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.example.demo.model.MyDtoInterface;

import jakarta.servlet.http.HttpSession;

public  class MyHttpTrasporter {
	
	private static HttpHeaders headers ;
	private static String authorization ;
	private static HttpEntity<MyDtoInterface> requestEntity ; 

	private MyHttpTrasporter() {
	
		headers= new HttpHeaders();
	   
	}
	
	public static HttpEntity<MyDtoInterface> authJsonDtoCarrier(HttpSession session, MyDtoInterface myDtoInterface) {
		
	    requestEntity = new HttpEntity<>(myDtoInterface, authJsonCarrier(session));
	   

		return requestEntity;
	}
	
	public static HttpHeaders authJsonCarrier(HttpSession session) {
		new MyHttpTrasporter();
		authorization=(String) session.getAttribute("Authorization");
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", authorization);

		return headers;

	}

	

}
