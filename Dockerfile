# Use an official OpenJDK runtime as the base image
FROM openjdk:11-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target folder into the container
COPY target/KabaddiLiveScoreBoard-0.0.1-SNAPSHOT.jar /app/KabaddiLiveScoreBoard-0.0.1-SNAPSHOT.jar

# Expose the port the app will run on (default Spring Boot port is 8080)
EXPOSE 8080

# Run the Spring Boot app
CMD ["java", "-jar", "/app/KabaddiLiveScoreBoard-0.0.1-SNAPSHOT.jar"]
