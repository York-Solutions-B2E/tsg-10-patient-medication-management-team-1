# Step 1: Build the application using Gradle
FROM openjdk:17-jdk-slim AS build

# Set the working directory
WORKDIR /opt/app

# Copy Gradle wrapper files
COPY gradlew build.gradle settings.gradle ./
COPY gradle gradle

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew dependencies --no-daemon || true

# Copy source code
COPY src ./src

# Build the application (skip tests)
RUN ./gradlew build -x test --no-daemon

# Step 2: Create a slim image to run the application
FROM openjdk:17-jdk-slim AS final

# Set the working directory
WORKDIR /opt/app

# Expose the application port
EXPOSE 8080

# Copy the built JAR file from the build stage
COPY --from=build /opt/app/build/libs/*.jar /opt/app/*.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]