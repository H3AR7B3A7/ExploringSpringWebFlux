# Spring Webflux

## Endpoints

http://localhost:8080/api/v1/product

http://localhost:8080/api/v1/product/1

http://localhost:8080/actuator

http://localhost:8080/swagger-ui

## Bash

```bash
productId=$(curl -X POST -H "Content-Type: application/json" -d '{"name": "Rinse Glass"}' http://localhost:8080/api/v1/product | jq -r '.productId')
```

```bash
curl -X POST -H "Content-Type: application/json" -d '{"productId":"$productId", "rating": 1}' http://localhost:8080/api/v1/rating
```

```bash
curl http://localhost:8080/api/v1/product
```

## Powershell 7

```shell
$productId = Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/v1/product -Body '{"name": "Rinse Glass"}' -ContentType "application/json" | Select-Object -ExpandProperty productId
```

```shell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/v1/rating -Body "{ `"productId`": `"$productId`", `"rating`": 1 }" -ContentType "application/json"
```

```shell
Invoke-RestMethod -Method Get -Uri http://localhost:8080/api/v1/product
```
_This does not display the output as soon as it becomes available.
This is because the default behavior of curl in bash is to display output as soon as it becomes available,
while in PowerShell the default behavior of Invoke-RestMethod is to wait until the entire response has been
received and then display it all at once._

To install curl using Chocolatey:

```shell
choco install curl
```

```shell
curl http://localhost:8080/api/v1/product
```

---
