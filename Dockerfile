# Usar a imagem base do OpenJDK 21 (ajustar conforme sua versão Java)
FROM eclipse-temurin:21-jdk-alpine

# Criar um diretório para a aplicação
WORKDIR /app

# Copiar o arquivo jar gerado pelo Maven para o contêiner
COPY target/playmakerhub-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta padrão do Spring Boot
EXPOSE 8080

# Definir o comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
