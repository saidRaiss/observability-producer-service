FROM maven:3.9.4-eclipse-temurin-21 AS build

RUN mvn dependency:copy -Dartifact=io.opentelemetry.javaagent:opentelemetry-javaagent:LATEST \
    -Dmdep.stripVersion=true -DoutputDirectory=./otel --no-transfer-progress --batch-mode

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
COPY --from=build /otel/opentelemetry-javaagent.jar ./opentelemetry-javaagent.jar

ENTRYPOINT ["java", "-javaagent:opentelemetry-javaagent.jar", "-jar", "app.jar"]
