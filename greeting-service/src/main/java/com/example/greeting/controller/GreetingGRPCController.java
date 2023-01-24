package com.example.greeting.controller;

import com.example.greeting.controller.util.LogInterceptor;
import com.example.greeting.model.GreetingRequest;
import com.example.greeting.model.GreetingResponse;
import com.example.greeting.model.GreetingServiceGrpc.GreetingServiceImplBase;
import com.example.greeting.service.GreetingService;

import net.devh.boot.grpc.server.service.GrpcService;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@GrpcService(interceptors = { LogInterceptor.class })
public class GreetingGRPCController extends GreetingServiceImplBase {

	private final GreetingService greetingService;

	@Override
	public void greet (GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
		final GreetingResponse greetingResponse = GreetingResponse.newBuilder().setGreeting(greetingService.getGreeting(request.getNum())).build();
		responseObserver.onNext(greetingResponse);
		responseObserver.onCompleted();
	}

}
