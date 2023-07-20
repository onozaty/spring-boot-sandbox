# Build Application
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /tmp
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN ["./gradlew", "bootJar"]

# Build Docker Image
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /tmp/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
