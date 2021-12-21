import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.21"
	kotlin("plugin.spring") version "1.5.21"
	kotlin("plugin.jpa") version "1.5.21"
	id("com.diffplug.spotless") version "5.15.0"
}

group = "rgomez"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

allprojects {
	repositories {
		mavenCentral()
	}
}

spotless {
	kotlin {
		ktlint()
			.userData(
				mapOf(
					"insert_final_newline" to "true",
					"disabled_rules" to "import-ordering",
					"indent_size" to "2",
					"continuation_indent_size" to "2"
				)
			)
	}
	kotlinGradle {
		ktlint()
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.1")
	implementation("org.springframework.boot:spring-boot-starter-data-rest:2.6.1")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb:2.6.1")

	implementation("org.springframework.boot:spring-boot-starter-web:2.6.1")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.arrow-kt:arrow-core:1.0.1")
	implementation("io.arrow-kt:arrow-fx-coroutines:1.0.1")


	// Contexts projects
	implementation(project(":context:user"))
	implementation(project(":context:group"))

	developmentOnly("org.springframework.boot:spring-boot-devtools:2.6.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
