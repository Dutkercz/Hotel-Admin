FROM maven:3.9.9-eclipse-temurin-21 AS build

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk

ENV MYSQL_DATABASE=meubanco
ENV MY_SQL_USERNAME=root
ENV MY_SQL_PASSWORD=1234

COPY --from=build target/*.jar hotel-system.jar

ENTRYPOINT ["java", "-jar", "hotel-system.jar"]