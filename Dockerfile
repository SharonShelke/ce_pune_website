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
COPY --from=build /app/target/*.jar app.jariable.
# The app tracks this variable via server.port=${PORT:8081}
# We expose a port for documentation, but the app binds to $PORT
EXPOSE 8080

COPY run.sh .
RUN chmod +x run.sh

CMD ["./run.sh"]