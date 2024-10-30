FROM eclipse-temurin:21

WORKDIR /app

# Copy the built Spring Boot application JAR file into the Docker container
COPY target/cms.jar contact-management-service.jar

# Expose the port on which the service will run
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "contact-management-service.jar"]
