FROM openjdk:17-jdk

WORKDIR /app
COPY target/Blog-Writer-01-0.0.1-SNAPSHOT.war app.war

ENTRYPOINT ["java", "-jar", "app.war"]
