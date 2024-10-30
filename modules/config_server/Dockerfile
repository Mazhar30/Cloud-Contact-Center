FROM eclipse-temurin:21

WORKDIR /app

# Copy the built Spring Boot application JAR file into the Docker container
COPY target/config.jar config-server.jar

# Expose the port on which the service will run
EXPOSE 8888

# Run the JAR file
ENTRYPOINT ["java", "-jar", "config-server.jar"]
