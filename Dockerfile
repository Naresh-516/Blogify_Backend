# ======== Stage 1: Build the application ========
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /build

# Copy everything (except what's in .dockerignore) into the image
COPY . .

# Build the WAR file using Maven
RUN mvn clean package -DskipTests

# ======== Stage 2: Run the application ========
FROM openjdk:17-jdk

WORKDIR /app

# Copy the built WAR from the builder stage
COPY --from=builder /build/target/Blog-Writer-01-0.0.1-SNAPSHOT.war app.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.war"]
