# ---------- BUILD STAGE ----------
FROM --platform=linux/arm64 maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first (for dependency cache)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build jar
RUN mvn clean package -DskipTests



# ---------- BUILD STAGE ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests


# ---------- RUN STAGE ----------
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
COPY --from=build /app/target/ce-pune-backend-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

