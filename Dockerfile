# Build stage
FROM eclipse-temurin:23-jdk AS build
WORKDIR /workspace

# Copy project files
COPY . .

# Ensure Maven Wrapper is executable (if present)
RUN chmod +x mvnw || true

# Build the application (skip tests for faster image build)
RUN ./mvnw -B -DskipTests package || mvn -version && mvn -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:23-jre
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"
WORKDIR /app

# Copy fat jar from build stage
COPY --from=build /workspace/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
