# Build stage
FROM maven:3.9.9-eclipse-temurin-23 AS build
WORKDIR /workspace

# Copy project files (leverage Docker cache by copying pom first)
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

COPY . .
RUN mvn -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:23-jre
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75"
WORKDIR /app

# Copy fat jar from build stage
COPY --from=build /workspace/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
