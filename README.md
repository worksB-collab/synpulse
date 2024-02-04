## How to run

### 1. clone repo

```bash
git clone https://github.com/...
```

### 2. build app and container

1. switch the folder path to `synpulse/demo`
2. build java app with `mvn package -DskipTests` to create .jar file
3. build docker image with `docker build -t demo-app .`
4. build docker compose and run with `docker-compose up`

### 3. test APIs

```bash
GET    http://localhost:8080/api/transactions/paginate
POST   http://localhost:8080/authenticate/login
```

## Swagger API UI

go to url : 'http://localhost:8080/swagger-ui/' on browser