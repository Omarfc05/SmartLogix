# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-24 AS build
WORKDIR /app

# Copiar todo el código fuente
COPY . .

# Compilar e instalar los módulos en el repositorio de Maven
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:24-jre
WORKDIR /app

ARG TARGET_PATH

COPY --from=build /app/ /app-build/

# Buscar el archivo .jar ejecutable generado en cualquier subcarpeta del módulo
RUN JAR_FILE=$(find /app-build/${TARGET_PATH} -type f -name "*.jar" ! -name "*plain*.jar" ! -name "*-sources.jar" | head -n 1) && \
    if [ -z "$JAR_FILE" ]; then \
      echo "ERROR: No se encontro archivo .jar ejecutable en /app-build/${TARGET_PATH}" && exit 1; \
    fi && \
    cp "$JAR_FILE" app.jar && \
    rm -rf /app-build

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]