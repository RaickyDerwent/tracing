FROM adoptopenjdk/openjdk11:jre-11.0.9_11-alpine
COPY target/hello-service-0.0.1-SNAPSHOT.jar hello-service.jar
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.22.1/opentelemetry-javaagent.jar .
EXPOSE 9090
ENTRYPOINT ["java","-javaagent:opentelemetry-javaagent.jar","-jar","/hello-service.jar"]