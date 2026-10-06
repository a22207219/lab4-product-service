FROM maven:3.9.16-eclipse-temurin-25 AS build
WORKDIR /build
COPY pom.xml .
COPY supplier-service/pom.xml supplier-service/pom.xml
RUN mvn -B -DskipTests dependency:go-offline
COPY supplier-service/src supplier-service/src
RUN mvn -B -pl supplier-service -am -DskipTests package
FROM eclipse-temurin:25-jre
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=build /build/supplier-service/target/supplier-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","app.jar"]
