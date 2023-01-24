package com.example.hello.greeting;

import com.example.greeting.model.GreetingRequest;
import com.example.greeting.model.GreetingResponse;
import com.example.greeting.model.GreetingServiceGrpc;

import net.devh.boot.grpc.client.inject.GrpcClient;

import org.springframework.stereotype.Service;

@Service("grpcGreetingService")
public class GrpcGreetingService implements IGreetingService {

	@GrpcClient("greeting-service")
	private GreetingServiceGrpc.GreetingServiceBlockingStub greetingService;

	@Override
	public String greet (int num) {
		final GreetingRequest greetingRequest = GreetingRequest.newBuilder().setNum(num).build();
		final GreetingResponse greetingResponse = greetingService.greet(greetingRequest);
		return greetingResponse.getGreeting();
	}

}
