# 1️⃣ Usamos la imagen oficial de Gradle con Java 17
FROM gradle:8.5-jdk17 AS build

# 2️⃣ Establecemos el directorio de trabajo
WORKDIR /app

# 3️⃣ Copiamos solo los archivos de configuración de Gradle primero (aprovechando la cache)
COPY build.gradle settings.gradle ./

# 4️⃣ Copiamos el código fuente
COPY src ./src

# 5️⃣ Construimos el proyecto (genera el JAR)
# Nueva línea
RUN gradle build -x test --no-daemon

# 6️⃣ Usamos una imagen más liviana de OpenJDK 17 para ejecutar el JAR
FROM openjdk:17-jdk-slim

WORKDIR /app

# 7️⃣ Copiamos el archivo JAR desde la etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# 8️⃣ Exponemos el puerto 8080
EXPOSE 8080

# 9️⃣ Ejecutamos la aplicación asegurando el puerto correcto
CMD ["java", "-jar", "app.jar", "--server.port=8080"]

# 10️⃣ Definimos variables de entorno para la conexión a PostgreSQL
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-cv6fop8gph6c73dmosq0-a:5432/dbtecnify
