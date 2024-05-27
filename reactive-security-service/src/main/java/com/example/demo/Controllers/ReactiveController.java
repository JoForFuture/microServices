package com.example.demo.Controllers;

import java.time.Duration;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.Person;
import com.example.demo.Services.ReactivePersonService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/reactive")
@RequiredArgsConstructor
public class ReactiveController {
	
	
	private final ReactivePersonService reactivePersonService;
	
	@GetMapping(value="/getAll",produces=MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Person> getAll()
	{
		return reactivePersonService.findAll().delayElements(Duration.ofMillis(50));
	}
	

}
