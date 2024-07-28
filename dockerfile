FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /Backend

COPY src/ /Backend/src
COPY pom.xml /Backend/pom.xml

RUN ["mvn", "clean", "install"]

FROM openjdk:17-jdk-buster

COPY --from=build /Backend/target/JSONUtil-0.0.1-SNAPSHOT.jar JSONUtil-0.0.1-SNAPSHOT.jar
#will publish this dependency jar to maven repository

