FROM eclipse-temurin:21

WORKDIR /app

# Copy the built Spring Boot application JAR file into the Docker container
COPY target/eureka.jar eureka-server.jar

# Expose the port on which the service will run
EXPOSE 8761

# Run the JAR file
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
