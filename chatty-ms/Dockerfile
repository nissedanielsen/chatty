# Use a base image with JDK (e.g., OpenJDK 17)
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot WAR file from your local machine to the container
COPY target/chatty-ms-0.0.1-SNAPSHOT.jar chatty-ms.jar

# Expose the port your Spring Boot app is running on
EXPOSE 8090

# Define the command to run the Spring Boot app as a WAR
ENTRYPOINT ["java", "-jar", "chatty-ms.jar"]