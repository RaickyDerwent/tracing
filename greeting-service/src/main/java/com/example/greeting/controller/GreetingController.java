package com.example.greeting.controller;

import com.example.greeting.controller.util.LogInterceptor;
import com.example.greeting.model.GreetingRequest;
import com.example.greeting.model.GreetingResponse;
import com.example.greeting.model.GreetingServiceGrpc.GreetingServiceImplBase;

import net.devh.boot.grpc.server.service.GrpcService;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import io.grpc.stub.StreamObserver;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@GrpcService(interceptors = { LogInterceptor.class })
public class GreetingController extends GreetingServiceImplBase {

	private static final String[] greetings = new String[]{
			"Have a good day",
			"Have a great day",
			"Have a wonderful day",
			"Have a fruitful day"
	};

	private final Meter meter = GlobalOpenTelemetry.meterBuilder("io.opentelemetry.metrics.greeting").build();

	private final HashMap<String, LongCounter> meterMap = new HashMap<>();

	@PostConstruct
	public void initCounter () {
		for (String greeting : greetings) {
			greeting = greeting.replaceAll(" ", "_");
			meterMap.put(greeting, meter.counterBuilder(greeting).setUnit("int").build());
		}
	}

	@Override
	public void greet (GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
		final String greeting = greetings[request.getNum()];
		meterMap.get(greeting).add(1);
		final GreetingResponse greetingResponse = GreetingResponse.newBuilder().setGreeting(greeting).build();
		responseObserver.onNext(greetingResponse);
		responseObserver.onCompleted();
	}

}
