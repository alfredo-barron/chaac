FROM openjdk:14-alpine
COPY ./app /app
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx1G", "-jar", "Middleware.jar"]