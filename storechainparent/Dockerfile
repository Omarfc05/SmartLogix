# Stage 1: Build de todo el proyecto multi-módulo
FROM maven:3.9.9-eclipse-temurin-24 AS build
WORKDIR /app

# Copiar el código fuente completo
COPY . .

# Compilar e instalar todos los módulos
RUN mvn clean package -DskipTests

# Stage 2: Runtime específico para cada servicio
FROM eclipse-temurin:24-jre
WORKDIR /app

# Declarar el argumento EXACTO que envía el docker-compose.yml
ARG JAR_FILE

# Copiar el resultado de la compilación
COPY --from=build /app/ /app-build/

# Buscar y copiar el JAR usando la ruta recibida en JAR_FILE
RUN echo "Buscando artefacto en: /app-build/${JAR_FILE}" && \
    FOUND_JAR=$(find /app-build -path "/app-build/${JAR_FILE}" ! -name "*plain*.jar" ! -name "*-sources.jar" | head -n 1) && \
    if [ -z "$FOUND_JAR" ]; then \
      echo "ERROR: No se encontró archivo .jar ejecutable con el patron /app-build/${JAR_FILE}" && exit 1; \
    fi && \
    echo "JAR asignado a este contenedor: $FOUND_JAR" && \
    cp "$FOUND_JAR" app.jar && \
    rm -rf /app-build

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]