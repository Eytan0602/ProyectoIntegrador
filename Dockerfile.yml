# Etapa de build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render asigna puerto con la variable $PORT
ENV PORT=10000
EXPOSE 10000

CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
