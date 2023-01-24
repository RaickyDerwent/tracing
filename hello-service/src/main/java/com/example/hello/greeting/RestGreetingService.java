package com.example.hello.greeting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

@Service("restGreetingService")
public class RestGreetingService implements IGreetingService {

	private RestTemplate restTemplate;

	@Value("${greeting.service.rest.endpoint}")
	private String greetingEndpoint;

	@PostConstruct
	public void createRestTemplate () {
		restTemplate = new RestTemplate();
	}

	@Override
	public String greet (int num) {
		Map<String, Integer> request = new HashMap<>();
		request.put("num", num);
		final Map<String, String> map = restTemplate.postForObject(greetingEndpoint + "/greet", request, Map.class);
		return map.get("greeting");
	}

}
