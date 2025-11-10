# Usamos una imagen base oficial de Java 17
FROM eclipse-temurin:17-jdk-jammy

# Copiamos el archivo .jar compilado (que Maven crea) al contenedor
COPY target/*.jar app.jar

# Exponemos el puerto (solo relevante para product-api)
EXPOSE 8080

# El comando para ejecutar la aplicación cuando el contenedor inicie
ENTRYPOINT ["java", "-jar", "/app.jar"]