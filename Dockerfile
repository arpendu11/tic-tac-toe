FROM openjdk:8-jdk-alpine
COPY target/tic-tac-toe-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]