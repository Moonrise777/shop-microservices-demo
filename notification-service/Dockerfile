# ---- Etapa 1: Construcción ----
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app

# Copiamos archivos de Maven Wrapper y el POM
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Damos permisos de ejecución al wrapper
RUN chmod +x mvnw

# Descargamos dependencias sin compilar todavía
RUN ./mvnw dependency:go-offline -B

# Copiamos el código fuente y compilamos
COPY src ./src
RUN ./mvnw clean package -DskipTests

# ---- Etapa 2: Ejecución ----
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copiamos el JAR desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
