FROM maven:3.9.9-amazoncorretto-17-al2023 AS build
# Set the working directory inside the container
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests



# Use an official OpenJDK runtime as the base image
FROM openjdk:24-slim-bullseye

WORKDIR /app
# Copy the JAR file from the target folder into the container
COPY --from=build /app/target/KabaddiLiveScoreBoard-0.0.1-SNAPSHOT.jar /app.jar

# Expose the port the app will run on (default Spring Boot port is 8080)
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app.jar"]
