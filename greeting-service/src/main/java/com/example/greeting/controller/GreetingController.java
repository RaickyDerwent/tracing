package com.example.greeting.controller;

import com.example.greeting.controller.util.LogInterceptor;
import com.example.greeting.model.GreetingRequest;
import com.example.greeting.model.GreetingResponse;
import com.example.greeting.model.GreetingServiceGrpc.GreetingServiceImplBase;

import net.devh.boot.grpc.server.service.GrpcService;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@GrpcService(interceptors = { LogInterceptor.class})
public class GreetingController extends GreetingServiceImplBase {

	private static final String[] greetings = new String[]{
			"Have a good day!",
			"Have a great day!",
			"Have a wonderful day!",
			"Have a fruitful day!"
	};

	@Override
	public void greet (GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
		final GreetingResponse greetingResponse = GreetingResponse.newBuilder().setGreeting(greetings[request.getNum()]).build();
		responseObserver.onNext(greetingResponse);
		responseObserver.onCompleted();
	}

}
