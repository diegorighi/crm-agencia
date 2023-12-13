# Define a imagem base
FROM openjdk:17-oracle

# Define o diretório de trabalho dentro do contêiner
WORKDIR /docker-agencia-crm

# Definir a variável de ambiente
ARG ENV_PROFILE=dev
ARG DEFAULT_PORT=8080
ARG SECRET_TOKEN=0123456789

ENV SPRINGBOOT_PORT=$DEFAULT_PORT

ENV MONGODB_URI=mongodb+srv://root:LGpi8711@crm-agencia-hml.jlcodpb.mongodb.net/agencia
ENV ENV_PROFILE=$ENV_PROFILE
ENV SECRET_TOKEN=$SECRET_TOKEN

#D Define a porta de execução do microserviço
EXPOSE $SPRINGBOOT_PORT

# Copia o arquivo JAR da aplicação para o diretório de trabalho dentro do contêiner
COPY target/agencia-crm-0.0.1-SNAPSHOT.jar .

# Define o comando padrão a ser executado quando o contêiner for iniciado
CMD ["java", "-jar", "agencia-crm-0.0.1-SNAPSHOT.jar"]