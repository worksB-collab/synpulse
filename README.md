# e-Banking Portal Backend Microservice README

## Overview

This microservice provides REST API endpoints to fetch paginated transactions for a given customer with total credit and
debit values in the current exchange rate. Developed using Java and Spring Boot, integrating Spring libraries for web,
security, data access, and messaging with Kafka. It includes Dockerization and Kubernetes deployment configurations.

## Code Organization

### Packages and Key Classes

- `config`: Configuration classes for security, Kafka, and application beans.
- `currency`: Service to fetch current exchange rates from an external API.
- `transaction`: Manages transaction operations, including Kafka consumption and paginated retrieval.
- `user`: Handles user authentication and details.
- `util`: Utility classes for JWT token management and JSON processing.
- `account`: Account related operations.

### Key Implementations

- **JWT Authentication**: For secure authentication of requests.
- **Kafka Integration**: For consuming and processing transaction data.
- **Restful API**: For transaction retrieval with pagination and currency conversion.
- **External API Integration**: For real-time exchange rate fetching.

## How to run

### 1. Clone repo

```bash
git clone https://github.com/worksB-collab/synpulse
```

### 2. Build app and container

1. Make sure Docker Desktop is running.
2. Build and run the service:
   1. With Docker Compose:
      1. Windows:
         1. Replace `{username}` in the jmockit path of windows profile in pom.xml.
            e.g. `<jmockit.path>C:\Users\billy\.m2\repository\org\jmockit\jmockit\1.49\jmockit-1.49.jar</jmockit.path>`.
         2. Build java app with `mvn package -Pwindows` to create .jar file.
      2. Mac:
         1. Replace `{username}` in the jmockit path of mac profile in pom.xml.
            e.g. `<jmockit.path>/Users/billy/.m2/repository/org/jmockit/jmockit/1.49/jmockit-1.49.jar</jmockit.path>`.
         2. Build java app with `mvn package -Pmac` to create .jar file.
      3. Build docker image with `docker build -t demo-app .`.
      4. Build docker compose and run with `docker-compose up`.

   2. With Kubernetes:
      1. Ensure `kubectl` is configured to communicate with your Kubernetes cluster.
      2. Ensure Kubernetes is enabled in Docker.
      3. Set the docker context to Docker Desktop on your machine by `kubectl config use-context docker-desktop`.
      4. Apply the Kubernetes configurations:
         ```bash
         kubectl apply -f deployment.yaml
         kubectl apply -f service.yaml
         ```

## Testing the APIs

After successfully deploying the service, you can test its functionality using the provided endpoints. This section
guides you through the process of logging in with two different users and accessing the system's features.

### Available Users

- **User 1:** "test" - has accounts and can retrieve transactions.
- **User 2:** "test2" - does not have accounts.

### Step 1: Login

To test the login functionality, send a POST request to the login endpoint for each user. This will authenticate the
user and provide a JWT token, which is required for subsequent API calls.

#### Login as "test"

```text
POST http://localhost:8080/authenticate/login
Content-Type: application/json

{
  "username": "test",
  "password": "test"
}
```

#### Login as "test2"

```text
POST http://localhost:8080/authenticate/login
Content-Type: application/json

{
  "username": "test2",
  "password": "test2"
}
```

#### Expected Response

Upon successful login, you will receive a JSON response containing a JWT token and a list of account IDs associated with
the user (only for "test" as "test2" has no accounts). You'll need the JWT token for authenticated requests to other
endpoints. Here's an example response structure for the "test" user:

```json
{
   "token": "<JWT_TOKEN>",
   "accountList": [
      1,
      2
   ]
}
```

### Step 2: Retrieve Paginated Transactions

After logging in, you can fetch paginated transaction details for a specific account. Ensure you use the JWT token
received from the login response in the `Authorization` header as `Bearer <JWT_TOKEN>`.

#### Fetching Transactions

Send a GET request to the paginated transactions' endpoint, specifying the account ID, page number, page size, and
target currency for conversion:

```text
GET http://localhost:8080/api/transactions/paginate?accountId=1&pageNumber=2&pageSize=2&targetCurrency=USD
Authorization: Bearer <JWT_TOKEN>
```

#### Expected Result

The response will include a list of transactions for the specified account, paginated according to your parameters,
along with the total credit and debit amounts converted into the target currency. Here's an example of the expected JSON
response:

```json
{
   "transactionList": [
      {
         "id": "9583ae7d-19dd-435d-ada3-f096dbd56d3b",
         "amount": "TRY 25.161887681773887",
         "currency": "TRY",
         "accountIban": "CH93-0000-0000-0000-0000-0",
         "valueDate": "2023-12-01",
         "description": "1"
      },
      {
         "id": "2bad4c41-204d-4d68-a242-b416da376ddf",
         "amount": "MAD 92.60597181766668",
         "currency": "MAD",
         "accountIban": "CH93-0000-0000-0000-0000-0",
         "valueDate": "2023-12-01",
         "description": "2"
      }
   ],
   "totalCredit": 3393.4116778945737123861,
   "totalDebit": 0
}
```

Note: Accessing this endpoint with an invalid token or as an unauthorized user will result in a 401 Unauthorized error.

## Swagger API UI

Access comprehensive API documentation and test endpoints interactively via Swagger UI:

```
http://localhost:8080/swagger-ui/
```

## Continuous Integration

View the GitHub Actions CI pipeline for the project:

```
https://github.com/worksB-collab/synpulse/actions/runs/7912539854
```