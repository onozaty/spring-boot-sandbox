# Build Application
FROM openjdk:11-jdk-slim-bullseye AS build
WORKDIR /tmp
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN ["./gradlew", "bootJar"]

# Build Docker Image
FROM openjdk:11-jre-slim-bullseye
COPY --from=build /tmp/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
