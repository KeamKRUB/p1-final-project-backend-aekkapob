# syntax=docker/dockerfile:1.6

FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q -e -DskipTests package || true

COPY src ./src
RUN mvn -q -e -DskipTests package

FROM eclipse-temurin:21-jre
ENV TZ=Asia/Bangkok
WORKDIR /app

COPY --from=build /app/target/*-SNAPSHOT.jar /app/app.jar

EXPOSE 8888
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
