package com.example.hello.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggingServiceImpl implements LoggingService {

	private static final Logger log = LoggerFactory.getLogger(LoggingServiceImpl.class.getPackage().getName() + ".HelloLoggingService");

	@Override
	public void logRequest (HttpServletRequest httpServletRequest, Object body) {
		if (httpServletRequest.getRequestURI().contains("ping") || httpServletRequest.getRequestURI().contains("actuator")) {
			return;
		}

		StringBuilder stringBuilder = new StringBuilder();
		Map<String, String> parameters = buildParametersMap(httpServletRequest);

		stringBuilder.append("REQUEST ");
		stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
		stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");

		if (!parameters.isEmpty()) {
			stringBuilder.append("parameters=[").append(parameters).append("] ");
		}

		if (body != null) {
			stringBuilder.append("body=[").append(body).append("]");
		}

		log.info(stringBuilder.toString());
	}

	@Override
	public void logResponse (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
		if (httpServletRequest.getRequestURI().contains("ping") || httpServletRequest.getRequestURI().contains("actuator")) {
			return;
		}

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("RESPONSE ");
		stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
		stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
		final Map<String, String> headersMap = buildHeadersMap(httpServletResponse);
		if (!headersMap.isEmpty()) {
			stringBuilder.append("responseHeaders=[").append(headersMap).append("] ");
		}
		stringBuilder.append("status=").append(httpServletResponse.getStatus()).append(" ");
		stringBuilder.append("responseBody=[").append(body).append("] ");

		log.info(stringBuilder.toString());
	}

	private Map<String, String> buildHeadersMap (HttpServletResponse response) {
		Map<String, String> map = new HashMap<>();

		Collection<String> headerNames = response.getHeaderNames();
		for (String header : headerNames) {
			map.put(header, response.getHeader(header));
		}

		return map;
	}

	private Map<String, String> buildParametersMap (HttpServletRequest httpServletRequest) {
		Map<String, String> resultMap = new HashMap<>();
		Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String key = parameterNames.nextElement();
			String value = httpServletRequest.getParameter(key);
			resultMap.put(key, value);
		}

		return resultMap;
	}

	private Map<String, String> buildHeadersMap (HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}

		return map;
	}

}
