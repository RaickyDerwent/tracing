package com.example.hello.controller.util;

import org.springframework.stereotype.Component;

import io.grpc.ForwardingServerCall.SimpleForwardingServerCall;
import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LogInterceptor implements ServerInterceptor {

	@Override
	public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall (ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
		log.info("Method = {}", call.getMethodDescriptor().getFullMethodName());
		ServerCall<ReqT, RespT> listener = new SimpleForwardingServerCall<>(call) {

			@Override
			public void sendMessage (RespT message) {
				log.info("Sending response: {}", message);
				super.sendMessage(message);
			}
		};

		return new SimpleForwardingServerCallListener<>(next.startCall(listener, headers)) {

			@Override
			public void onMessage (ReqT message) {
				log.info("Received request: {}", message);
				super.onMessage(message);
			}
		};
	}

}