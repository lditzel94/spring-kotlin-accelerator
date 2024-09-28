# Usar la imagen base de GraalVM con JDK 21
FROM amazoncorretto:21

# Etiqueta del mantenedor (cambia esto a tu información)
LABEL name=kotlin-accelerator

# Definir variables de entorno, si es necesario
ENV ACTIVE_PROFILE "local"

# Especificar el directorio de trabajo
WORKDIR /opt/kotlin-accelerator/

# Ejemplo: Copiar tu aplicación JAR a la imagen
COPY build/libs/*.jar /opt/kotlin-accelerator/

# Comando de inicio de la aplicación, ajusta esto según tus necesidades
CMD ["java","--add-opens","java.base/java.lang=ALL-UNNAMED","-jar", "spring-kotlin-accelerator-1.0.0.jar"]
