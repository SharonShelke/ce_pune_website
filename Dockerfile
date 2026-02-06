# ---------- BUILD STAGE ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first (for dependency cache)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build jar
RUN mvn clean package -DskipTests

# ---------- RUN STAGE ----------
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
COPY --from=build /app/target/ce-pune-backend-1.0-SNAPSHOT.jar ce-pune-backend-1.0-SNAPSHOT.jar

# Render sets the PORT environment variable.
# The app tracks this variable via server.port=${PORT:8081}
# We expose a port for documentation, but the app binds to $PORT
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "ce-pune-backend-1.0-SNAPSHOT.jar"]