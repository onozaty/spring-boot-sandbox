# Spring Bootで作ったアプリケーションをDockerで動かす

* https://spring.io/guides/gs/spring-boot-docker/

## Spring Initializr で雛形を作る

Spring Initializr でプロジェクトの雛形を作成します。

* https://start.spring.io/

Dependencies として、Spring Web を選択します。

GENERATE ボタンでダウンロードされたzipファイルを展開し、IDEでインポートします。

Gradle Projectにしました。  
`build.gradle` は下記の通りです。

```gradle
plugins {
	id 'org.springframework.boot' version '2.6.4'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'java'
}

group = 'com.github.onozaty'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

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

`Application.java` に http://localhost:8080 でのリクエストを受け付けるメソッドを用意します

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @RequestMapping("/")
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
FROM openjdk:11-jre-slim-bullseye
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

`docker build`します。

```
docker build --build-arg JAR_FILE=build/libs/\*.jar -t onozaty/spring-boot-26-docker
```

`docker run`で実行します。

```
docker run -p 8080:8080 onozaty/spring-boot-26-docker
```

下記URLにアクセスし、`Hello Docker World` と表示されることを確認します。

* http://localhost:8080/

