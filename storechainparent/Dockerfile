# Stage 1: Compilar el proyecto multi-módulo
FROM maven:3.9.9-eclipse-temurin-24 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:24-jre
WORKDIR /app
ARG JAR_FILE
# Copia el archivo .jar buscando dinámicamente en la ruta especificada
COPY --from=build /app/${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]