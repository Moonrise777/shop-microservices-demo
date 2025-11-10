# Etapa 1: construir la aplicación con Maven
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app

# Copiar pom.xml y dependencias primero para aprovechar cache
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

# Copiar el resto del código y compilar
COPY src src
RUN ./mvnw clean package -DskipTests

# Etapa 2: imagen final más ligera
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copiamos el .jar generado desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
