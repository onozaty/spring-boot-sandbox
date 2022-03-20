# Spring Bootで作ったアプリケーションをDockerで動かす

* https://spring.pleiades.io/guides/gs/spring-boot-docker/

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
