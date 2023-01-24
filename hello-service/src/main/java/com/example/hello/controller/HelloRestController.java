package com.example.hello.controller;

import com.example.hello.greeting.IGreetingService;
import com.example.hello.model.HelloRequest;
import com.example.hello.model.HelloResponse;
import com.example.hello.service.HelloService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

	public final IGreetingService greetingService;
	private final HelloService helloService;

	public HelloRestController (@Qualifier("restGreetingService") IGreetingService greetingService, HelloService helloService) {
		this.greetingService = greetingService;
		this.helloService = helloService;
	}

	@PostMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HelloResponse> hello (@RequestBody HelloRequest helloRequest) {
		final String responseStr = helloService.hello(helloRequest.getName(), greetingService);
		return ResponseEntity.ok(HelloResponse.newBuilder().setHello(responseStr).build());
	}

}
