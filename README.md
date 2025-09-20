# Football Project

Dies ist ein Fullstack-Projekt mit **Spring Boot Backend** und optional einem **Frontend**.  
Die Services laufen lokal über **Docker Compose** und können später auch getrennt oder gemeinsam deployed werden.

## Technologien & Versionen

- **Java:** 21
- **Spring Boot:** 3.5.5
- **Maven:** 4.0.0
- **PostgreSQL:** 15.4
- **Docker:** 28.4

## Backend

### Global commands
```bash
# Boot API
./mvnw spring-boot:run 

# Create new network
docker-compose down
docker network prune
docker-compose up --build
```

### DEV Services

```bash
# Cleanup old services
docker-compose -f ./docker-compose.dev.yml down
docker network prune -f
docker volume prune -f

# build dev environment
docker-compose -f ../docker-compose.dev.yml up --build

# Access api via terminal
psql -h localhost -p 5432 -U football_user -d football_db
```

> **Backend:** http://localhost:8080  
> **Datenbank:** Port 5432

### Docker Image für Deployment
```bash
cd backend

# Build project
./mvnw clean install -DskipTests

# Build image
docker build -t football-api .

# Tag image
docker tag football-api brobuddy/football-api:latest

# Push image
docker push brobuddy/football-api:latest
```

