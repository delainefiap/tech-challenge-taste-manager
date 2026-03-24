# syntax=docker/dockerfile:1.4
# Estágio de Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only the Maven wrapper and pom first to leverage Docker cache for dependencies
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Download dependencies (cacheable layer) using BuildKit cache mount for Maven local repo
# Requires BuildKit: set DOCKER_BUILDKIT=1 when running `docker build` or enable in daemon
RUN --mount=type=cache,target=/root/.m2 mvn -B dependency:go-offline

# Now copy sources and build (use the same cache mount so dependencies are reused)
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B package -DskipTests

# Estágio de Produção
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy jar from build stage using wildcard to avoid hardcoded name
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Start the application directly. Spring Boot will run import.sql from the path provided by SPRING_SQL_INIT settings.
# The jar is at /app/app.jar because WORKDIR is /app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
