package com.example.greeting.controller.util;

import org.springframework.stereotype.Component;

import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class GrpcInterceptor implements ServerInterceptor {

	@Override
	public <M, R> ServerCall.Listener<M> interceptCall (ServerCall<M, R> call, Metadata headers, ServerCallHandler<M, R> next) {

		GrpcServerCall grpcServerCall = new GrpcServerCall(call);

		ServerCall.Listener listener = next.startCall(grpcServerCall, headers);

		return new GrpcForwardingServerCallListener<M>(call.getMethodDescriptor(), listener) {
			@Override
			public void onMessage (M message) {
				log.info("Method: {}, Message: {}", methodName, message);
				super.onMessage(message);
			}
		};
	}

	private class GrpcServerCall<M, R> extends ServerCall<M, R> {

		ServerCall<M, R> serverCall;

		protected GrpcServerCall (ServerCall<M, R> serverCall) {
			this.serverCall = serverCall;
		}

		@Override
		public void request (int numMessages) {
			serverCall.request(numMessages);
		}

		@Override
		public void sendHeaders (Metadata headers) {
			serverCall.sendHeaders(headers);
		}

		@Override
		public void sendMessage (R message) {
			log.info("Method: {}, Response: {}", serverCall.getMethodDescriptor().getFullMethodName(), message);
			serverCall.sendMessage(message);
		}

		@Override
		public void close (Status status, Metadata trailers) {
			serverCall.close(status, trailers);
		}

		@Override
		public boolean isCancelled () {
			return serverCall.isCancelled();
		}

		@Override
		public MethodDescriptor<M, R> getMethodDescriptor () {
			return serverCall.getMethodDescriptor();
		}

	}

	private class GrpcForwardingServerCallListener<M> extends io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener<M> {

		String methodName;

		protected GrpcForwardingServerCallListener (MethodDescriptor method, ServerCall.Listener<M> listener) {
			super(listener);
			methodName = method.getFullMethodName();
		}

	}

}