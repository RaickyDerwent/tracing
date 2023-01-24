package com.example.greeting.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.DispatcherType;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RestLogInterceptor implements HandlerInterceptor {

	@Autowired
	LoggingService loggingService;

	@Override
	public boolean preHandle (HttpServletRequest request, HttpServletResponse response,
	                          Object handler) {

		if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
				&& (request.getMethod().equals(HttpMethod.GET.name()) || request.getMethod().equals(HttpMethod.POST.name()))) {
			loggingService.logRequest(request, null);
		}

		return true;
	}

}