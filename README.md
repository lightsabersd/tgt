My Retail App
Written in Micronaut and Kotlin. Uses MongoDB

Endpoints
GET: /products/id
PUT: /products/id

to start Mongo, 
run ```docker-compose up```

to run the web service, 
run ```./gradlew run```

to run tests, 
```./gradlew test```

After the app is running, you can use curl to add data: 
```curl --location --request PUT 'localhost:8080/products/15117729' \
--header 'Content-Type: application/json' \
--data-raw '{"value": "2.95", "currencyCode": "USD"}'
```
OR you can run this bash script (/env/bootstrap.sh to add some data for product ids: 
```15117729, 16483589, 16696652, 16752456, 15643793```

to get information

```curl --location --request GET 'localhost:8080/products/15117729'```
