## How to run

### 1. Clone repo

```bash
git clone https://github.com/worksB-collab/synpulse
```

### 2. Build app and container

1. Build java app with `mvn package -Pdev` to create .jar file.
2. Make sure Docker Desktop is running.
3. Build docker image with `docker build -t demo-app .`.
4. Build and run the service:
   1. With Docker Compose: build docker compose and run with `docker-compose up`.
   2. With Kubernetes:
      1. Ensure `kubectl` is configured to communicate with your Kubernetes cluster.
      2. Ensure Kubernetes is enabled in Docker.
      3. Set the docker context to Docker Desktop on your machine by `kubectl config use-context docker-desktop`.
      4. Apply the Kubernetes configurations:
      ```bash
      kubectl apply -f deployment.yaml
      kubectl apply -f service.yaml
      ```

### 3. Test APIs

After deploying the service, use the following commands to test the APIs:

* There are two users, "test" and "test2".
* Only "test" has accounts.

#### Login
Request by:
```
POST   http://localhost:8080/authenticate/login
```
with body
```json
{
  "username": "test",
  "password": "test"
}
```
or
```json
{
   "username": "test2",
   "password": "test2"
}
```
Example result:
```json
{
   "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJQLTAxMjM0NTY3ODkiLCJpYXQiOjE3MDc5ODQxNjYsImV4cCI6MTcwODA3MDU2Nn0.7pXr7ZBbwop23MRHytoglIWUzGnOeaJmQJ2hXY-3tSTr_bMIXhWVCgjrtGIG7dSRtWWPvnUvRwjRRjL8RWdFJA",
   "accountList": [
      1,
      2
   ]
}
```

#### Get paginated transactions
Request by:
```
GET    http://localhost:8080/api/transactions/paginate
```
Example request:
```
GET    http://localhost:8080/api/transactions/paginate?accountId=1&pageNumber=2&pageSize=2&targetCurrency=USD
```

* While using invalid token or unauthorized user, a 401 Unauthorized is expected.

## Swagger API UI

To access the Swagger API UI, navigate to:

```
http://localhost:8080/swagger-ui/
```

## Continuous Integration

GitHub Action
```
https://github.com/worksB-collab/synpulse/actions/runs/7912539854
```