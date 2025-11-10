# ---- Etapa 1: Compilación ----
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos el archivo pom.xml y descargamos dependencias primero
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el resto del código y compilamos
COPY src ./src
RUN mvn clean package -DskipTests

# ---- Etapa 2: Ejecución ----
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copiamos el .jar desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Render define automáticamente $PORT, lo usamos aquí
ENV PORT=8080
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
