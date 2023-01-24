package com.example.hello.controller.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;

@Configuration
public class ApiConfig {

	@Bean
	public ProtobufJsonFormatHttpMessageConverter protobufHttpMessageConverter () {
		return new ProtobufJsonFormatHttpMessageConverter();
	}

}
