plugins {
    java
    id("org.springframework.boot") version "3.0.4"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "ru.mai.zaytsevvagen"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.kafka:spring-kafka:3.0.5")
    testImplementation("org.springframework.kafka:spring-kafka-test:3.0.5")

    implementation("org.modelmapper:modelmapper:0.7.5")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.4")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // https://mvnrepository.com/artifact/org.liquibase/liquibase-core

    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
