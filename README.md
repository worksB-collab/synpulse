## How to run

### 1. Clone repo

```bash
git clone https://github.com/...
```

### 2. Build app and container

1. Build java app with `mvn package -Pdev` to create .jar file.
2. Build docker image with `docker build -t demo-app .`.
3. Build with docker-compose or k8s
   1. With docker compose: build docker compose and run with `docker-compose up`.
   2. With Kubernetes:
      1. Ensure `kubectl` is configured to communicate with your Kubernetes cluster.
      2. Apply the Kubernetes configurations:
      ```bash
      kubectl apply -f deployment.yml
      kubectl apply -f service.yml
      ```

### 3. test APIs

After deploying your services, use the following commands to test the APIs:

```
GET    http://localhost:8080/api/transactions/paginate
POST   http://localhost:8080/authenticate/login
```

## Swagger API UI

To access the Swagger API UI, navigate to:

```
http://localhost:8080/swagger-ui/
```