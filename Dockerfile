# Use a slim OpenJDK image for smaller size (optional)
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the .war file into the container
COPY target/Blog-Writer-01-0.0.1-SNAPSHOT.war app.war

# Expose the port your Spring Boot app will run on (default is 8080)
EXPOSE 8080

# Set the entry point to run the .war file
ENTRYPOINT ["java", "-jar", "app.war"]
