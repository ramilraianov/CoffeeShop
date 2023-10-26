Для локальной работы можно использовать [docker-compose.yml](https://github.com/ramilraianov/CoffeeShop/blob/master/env2/docker-compose.yml)

Примеры запросов

```
###
POST /api/v1/events
Host: localhost:8080
Content-Type: application/json

{
  "eventType": "RESERVED",
  "orderId": 1,
  "customerId": 1,
  "expectedTime": "2023-10-21T12:34:56",
  "employeeId": 1,
  "items": [
    {
      "id": 5
    },
    {
      "id": 6
    }
  ],
  "price": "100",
  "time": "2023-10-21T12:34:56"
}

###
POST /api/v1/events
Host: localhost:8080
Content-Type: application/json

{
  "eventType": "CANCELED",
  "orderId": 1,
  "reason": "Cancel reason",
  "employeeId": 1,
  "time": "2023-10-21T12:34:56"
}

###
POST /api/v1/events
Host: localhost:8080
Content-Type: application/json

{
  "eventType": "PROCESSING",
  "orderId": 1,
  "employeeId": 1,
  "time": "2023-10-21T12:34:56"
}


###
POST /api/v1/events
Host: localhost:8080
Content-Type: application/json

{
  "eventType": "READY",
  "orderId": 1,
  "employeeId": 1,
  "time": "2023-10-21T12:34:56"
}


###
POST /api/v1/events
Host: localhost:8080
Content-Type: application/json

{
  "eventType": "ISSUED",
  "orderId": 1,
  "employeeId": 1,
  "time": "2023-10-21T12:34:56"
}

```
