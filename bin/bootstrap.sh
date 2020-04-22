#!/bin/bash

curl --location --request PUT 'localhost:8080/products/15117729' \
--header 'Content-Type: application/json' \
--data-raw '{"value": "2.95", "currencyCode": "USD"}'

curl --location --request PUT 'localhost:8080/products/16483589' \
--header 'Content-Type: application/json' \
--data-raw '{"value": "23.95", "currencyCode": "USD"}'

curl --location --request PUT 'localhost:8080/products/16696652' \
--header 'Content-Type: application/json' \
--data-raw '{"value": "4.95", "currencyCode": "USD"}'

curl --location --request PUT 'localhost:8080/products/16752456' \
--header 'Content-Type: application/json' \
--data-raw '{"value": "9.95", "currencyCode": "USD"}'

curl --location --request PUT 'localhost:8080/products/15643793' \
--header 'Content-Type: application/json' \
--data-raw '{"value": "12.95", "currencyCode": "USD"}'
