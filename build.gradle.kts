import org.gradle.api.tasks.testing.logging.TestExceptionFormat

buildscript {
   repositories {
      jcenter()
      mavenCentral()
      maven {
         url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
      }
      maven {
         url = uri("https://plugins.gradle.org/m2/")
      }
   }
}

plugins {
   java
   `java-library`
   id("java-library")
   id("maven-publish")
   signing
   maven
   `maven-publish`
   kotlin("jvm").version(Libs.kotlinVersion)
}

allprojects {
   apply(plugin = "org.jetbrains.kotlin.jvm")

   group = Libs.org
   version = Ci.version

   dependencies {
      implementation(Libs.Kotest.Api)
      api(Libs.MockServer.netty)
      api(Libs.MockServer.javaClient)
      testImplementation(Libs.Kotest.Assertions)
      testImplementation(Libs.Kotest.Junit5)
      testImplementation(Libs.Fuel.core)
   }

   tasks.named<Test>("test") {
      useJUnitPlatform()
      testLogging {
         showExceptions = true
         showStandardStreams = true
         exceptionFormat = TestExceptionFormat.FULL
      }
   }

   tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
      kotlinOptions.jvmTarget = "1.8"
   }

   repositories {
      mavenLocal()
      mavenCentral()
      maven {
         url = uri("https://oss.sonatype.org/content/repositories/snapshots")
      }
   }
}

apply("./publish.gradle.kts")
