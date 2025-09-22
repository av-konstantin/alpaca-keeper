# Hello World Spring Boot (Java 23)

A minimal Spring Boot 3.x application with a Thymeleaf MVC frontend and PostgreSQL persistence.

- GET / renders a Thymeleaf page showing "Hello World", current time, and a list of messages.
- POST /messages adds a new Message (text) and redirects back to "/".

## Tech
- Java 23
- Spring Boot 3.x
- Spring Web, Thymeleaf, Data JPA, Actuator
- PostgreSQL (Hibernate/JPA, ddl-auto=update)
- Maven (wrapper included)

## Run locally
1. Start PostgreSQL and create a database (example: demo) and user.
2. Export environment variables:

```bash
export SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/demo?sslmode=disable"
export SPRING_DATASOURCE_USERNAME="demo"
export SPRING_DATASOURCE_PASSWORD="demo"
```

3. Run the app:

```bash
./mvnw spring-boot:run
```

Open http://localhost:8080

## Build a jar
```bash
./mvnw -DskipTests package
```
The executable jar will be under `target/`.

## Docker
Build the image:
```bash
docker build -t demo-app:latest .
```
Run the container:
```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:postgresql://<host>:5432/<db>?sslmode=require" \
  -e SPRING_DATASOURCE_USERNAME="<user>" \
  -e SPRING_DATASOURCE_PASSWORD="<password" \
  demo-app:latest
```

## Azure Container Apps
You can deploy using the included Dockerfile (Build from source) or build and push the image to a container registry (e.g., ACR or Docker Hub), then deploy.

- Set environment variables in the Container App:
  - SPRING_DATASOURCE_URL
  - SPRING_DATASOURCE_USERNAME
  - SPRING_DATASOURCE_PASSWORD
- Target port: 8080
- Enable external ingress
- Health endpoints: `/actuator/health` and `/actuator/info`

## Configuration
application.properties already includes:
```
server.port=8080
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.jdbc.time_zone=UTC
spring.jpa.open-in-view=false
management.endpoints.web.exposure.include=health,info
```
