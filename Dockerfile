FROM openjdk:11-jre-slim

WORKDIR /app

# Copy the application JAR file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1-SNAPSHOT.jar

# Specify the command to run your application
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
