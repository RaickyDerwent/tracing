package com.example.hello.controller;

import com.example.greeting.model.GreetingRequest;
import com.example.greeting.model.GreetingServiceGrpc;
import com.example.hello.controller.util.LogInterceptor;
import com.example.hello.model.HelloRequest;
import com.example.hello.model.HelloResponse;
import com.example.hello.model.HelloServiceGrpc.HelloServiceImplBase;

import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.concurrent.ThreadLocalRandom;

import io.grpc.stub.StreamObserver;

@GrpcService(interceptors = { LogInterceptor.class})
public class HelloController extends HelloServiceImplBase {

	@GrpcClient("greeting-service")
	private GreetingServiceGrpc.GreetingServiceBlockingStub greetingService;

	@Override
	public void hello (HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
		int greetingRequestNum = ThreadLocalRandom.current().nextInt(0, 4);
		final GreetingRequest greetingRequest = GreetingRequest.newBuilder().setNum(greetingRequestNum).build();
		final String greeting = greetingService.greet(greetingRequest).getGreeting();

		final String responseStr = "Hello " + request.getName() + "! " + greeting;
		final HelloResponse helloResponse = HelloResponse.newBuilder().setHello(responseStr).build();
		responseObserver.onNext(helloResponse);
		responseObserver.onCompleted();
	}

}
