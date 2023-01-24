package com.example.hello.controller;

import com.example.hello.controller.util.LogInterceptor;
import com.example.hello.greeting.IGreetingService;
import com.example.hello.model.HelloRequest;
import com.example.hello.model.HelloResponse;
import com.example.hello.model.HelloServiceGrpc.HelloServiceImplBase;
import com.example.hello.service.HelloService;

import net.devh.boot.grpc.server.service.GrpcService;

import org.springframework.beans.factory.annotation.Qualifier;

import io.grpc.stub.StreamObserver;

@GrpcService(interceptors = { LogInterceptor.class })
public class HelloGRPCController extends HelloServiceImplBase {

	private final HelloService helloService;
	private final IGreetingService greetingService;

	public HelloGRPCController (HelloService helloService, @Qualifier("grpcGreetingService") IGreetingService greetingService) {
		this.helloService = helloService;
		this.greetingService = greetingService;
	}

	@Override
	public void hello (HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
		String responseStr = helloService.hello(request.getName(), greetingService);
		final HelloResponse helloResponse = HelloResponse.newBuilder().setHello(responseStr).build();
		responseObserver.onNext(helloResponse);
		responseObserver.onCompleted();
	}

}
