plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    // Other contexts dependencies
    implementation(project(":context:shared"))

    // Domain dependencies

    // Interaction dependencies

    // Primary adapter dependencies
    implementation("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.6.1")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.1")

    implementation("org.axonframework:axon-messaging:${property("axon-version")}")
    implementation("org.axonframework:axon-configuration:${property("axon-version")}")

    // Secondary adapter dependencies
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:2.6.1")

    // Common dependencies
    implementation("javax.inject:javax.inject:1")

    implementation("io.arrow-kt:arrow-core:1.0.1")
    implementation("io.arrow-kt:arrow-fx-coroutines:1.0.1")
}