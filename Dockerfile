# Estágio de Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only the Maven wrapper and pom first to leverage Docker cache for dependencies
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Download dependencies (cacheable layer)
RUN mvn -B dependency:go-offline

# Now copy sources and build
COPY src ./src
RUN mvn -B package -DskipTests

# Estágio de Produção
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy jar from build stage using wildcard to avoid hardcoded name
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Start the application directly. Spring Boot will run import.sql from the path provided by SPRING_SQL_INIT settings.
# The jar is at /app/app.jar because WORKDIR is /app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
