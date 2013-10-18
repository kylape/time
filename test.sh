#!/bin/bash

curl -H "Content-Type: application/json" -d '{"firstName":"Kyle","lastName":"Lape","email":"kyle.lape@gmail.com"}' http://localhost:8080/time/services/user
curl -H "Content-Type: application/json" -d '{"name":"Case work"}' http://localhost:8080/time/services/action
curl -H "Content-Type: application/json" -d '{"user":{"id":"1"},"action":{"id":"2"}}' http://localhost:8080/time/services/entry
curl -H "Accept: application/json" http://localhost:8080/time/services/entry/3
