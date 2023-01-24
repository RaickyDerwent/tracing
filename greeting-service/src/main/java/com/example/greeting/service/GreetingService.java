package com.example.greeting.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

	private static final String[] greetings = new String[]{
			"Have a good day",
			"Have a great day",
			"Have a wonderful day",
			"Have a fruitful day"
	};

	public String getGreeting (int num) {
		return greetings[num];
	}

}
