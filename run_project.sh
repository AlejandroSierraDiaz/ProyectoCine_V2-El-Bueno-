#!/bin/bash
# Script para ejecutar Cine V2 con la configuración correcta

# Configurar JAVA_HOME para usar Java 21 (necesario por compatibilidad con Lombok)
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64

# Asegurar que el contenedor de base de datos esté corriendo
echo "Iniciando base de datos..."
docker-compose up -d

# Ejecutar la aplicación
echo "Iniciando aplicación Spring Boot..."
./mvnw spring-boot:run
