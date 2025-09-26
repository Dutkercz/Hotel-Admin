# Stage 1: Build da aplicação com Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia o pom.xml e baixa dependências
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Copia o código fonte
COPY src ./src

# Builda o projeto, pulando testes (remova -DskipTests para rodar testes)
RUN mvn clean package -DskipTests

# Stage 2: Imagem runtime leve para rodar a app
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copia o jar gerado no build anterior
COPY --from=build /app/target/*.jar app.jar

# Exponha a porta 8080 (a padrão do Spring Boot)
EXPOSE 8080

# Defina as variáveis de ambiente padrão (pode sobrescrever via docker-compose)
ENV MYSQL_DATABASE=db_hotel_admin
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=1234

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]