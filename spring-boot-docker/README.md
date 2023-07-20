# Spring Bootで作ったアプリケーションをDockerで動かす

* https://spring.io/guides/gs/spring-boot-docker/

バージョン情報は下記の通りです。

* Spring Boot 3.1.2
* Java 17
* Gradle 7.6.2

## Spring Initializr で雛形を作る

Spring Initializr でプロジェクトの雛形を作成します。

* https://start.spring.io/

Dependencies として、Spring Web を選択します。

GENERATE ボタンでダウンロードされたzipファイルを展開し、IDEでインポートします。

Gradle Projectにしました。  
`build.gradle` は下記の通りです。

```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.1.2'
    id 'io.spring.dependency-management' version '1.1.2'
}

group = 'com.github.onozaty'
version = '0.0.1-SNAPSHOT'

java {
    sourceCompatibility = '17'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
    useJUnitPlatform()
}
```

## アプリケーション作成

`Application.java` に http://localhost:8080 でのリクエストを受け付けるメソッドを用意します。

```java
package com.github.onozaty.springboot.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @GetMapping("/")
    public String home() {
        return "Hello Docker World";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

アプリケーションを起動して、 http://localhost:8080 にアクセスすると、`Hello Docker World`が返却されます。

## コンテナ化

`Dockerfile` として下記を用意します。

```
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

Gradleでbuildした後に、`docker build`します。

```
docker build --build-arg JAR_FILE=build/libs/\*.jar -t onozaty/spring-boot-docker .
```

`docker run`で実行します。

```
docker run -p 8080:8080 onozaty/spring-boot-docker
```

下記URLにアクセスし、`Hello Docker World` と表示されることを確認します。

* http://localhost:8080/

## マルチステージビルドに変更

`Dockerfile` を下記のように変更します。

```
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
```

`docker build`します。

```
docker build -t onozaty/spring-boot-docker .
```

## Cloud Native Buildpacks

Cloud Native Buildpacks を使ってイメージを作成するための`bootBuildImage`タスクがあります。この際にDockerfileは不要です。

```
./gradlew bootBuildImage
```

```
docker run -p 8080:8080 spring-boot-docker:0.0.1-SNAPSHOT
```
