FROM eclipse-temurin:21

WORKDIR /app

# Copy the built Spring Boot application JAR file into the Docker container
COPY target/irs.jar interaction-management-service.jar

# Expose the port on which the service will run
EXPOSE 8081

# Run the JAR file
ENTRYPOINT ["java", "-jar", "interaction-management-service.jar"]
