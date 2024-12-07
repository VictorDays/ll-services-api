# Etapa de build
FROM maven:3.9.2-eclipse-temurin-17 as build

# Define o diretório de trabalho dentro do container
WORKDIR /workspace

# Copia o código-fonte para o container
COPY . .

# Executa o build do projeto com configuração do tipo "uber-jar"
RUN mvn clean package -DskipTests -Pnative

# Etapa final (imagem com OpenJDK)
FROM openjdk:21-jdk-slim

# Expõe a porta onde o serviço estará rodando
EXPOSE 8080

# Copia o JAR gerado na etapa anterior
COPY --from=build /workspace/target/llservicos-1.0-SNAPSHOT-runner.jar /app.jar

# Comando para rodar o aplicativo
ENTRYPOINT ["java", "-jar", "/app.jar"]
