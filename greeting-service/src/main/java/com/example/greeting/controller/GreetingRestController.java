package com.example.greeting.controller;

import com.example.greeting.model.GreetingRequest;
import com.example.greeting.model.GreetingResponse;
import com.example.greeting.service.GreetingService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GreetingRestController {

	private final GreetingService greetingService;

	@PostMapping(path = "/greet", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GreetingResponse> greet (@RequestBody GreetingRequest request) {
		final String greeting = greetingService.getGreeting(request.getNum());
		GreetingResponse greetingResponse = GreetingResponse.newBuilder().setGreeting(greeting).build();
		return ResponseEntity.ok(greetingResponse);
	}

}
