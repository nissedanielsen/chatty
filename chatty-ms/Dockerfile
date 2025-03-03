# Use a base image with JDK (e.g., OpenJDK 17) for building the application
FROM openjdk:17-jdk-slim AS build

# Install Maven globally
RUN apt-get update && apt-get install -y maven

# Set the working directory for building the project
WORKDIR /app

# Copy the pom.xml and the source code to the container
COPY pom.xml ./
COPY src ./src

# Run Maven to build the project and generate the JAR file
RUN mvn clean package -DskipTests

# Use a smaller base image for the final application
FROM openjdk:17-jdk-slim

# Set the working directory for the app
WORKDIR /app

# Copy the JAR file from the build stage to the final image
COPY --from=build /app/target/chatty-ms-0.0.1-SNAPSHOT.jar chatty-ms.jar

# Expose the port your Spring Boot app is running on
EXPOSE 8090

# Define the command to run the Spring Boot app as a JAR
ENTRYPOINT ["java", "-jar", "chatty-ms.jar"]
