package com.example.hello.service;

import com.example.hello.greeting.IGreetingService;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class HelloService {

	public String hello (String name, IGreetingService greetingService) {
		int greetingRequestNum = ThreadLocalRandom.current().nextInt(0, 4);
		final String greeting = greetingService.greet(greetingRequestNum);
		return "Hello " + name + "! " + greeting;
	}

}
