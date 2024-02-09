# Use a base image with Java 11
FROM openjdk:11-jre-slim

# Set the working directory in the Docker container
WORKDIR /app

# Copy the built JAR file from your host machine into the Docker container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot application runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]