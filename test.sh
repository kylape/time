#!/bin/bash

echo -n add some users...
curl -H "Content-Type: application/json" -d '{"firstName":"Kyle","lastName":"Lape","email":"kyle.lape@gmail.com"}' http://localhost:8080/time/services/user
curl -H "Content-Type: application/json" -d '{"firstName":"William","lastName":"Siqueira","email":"wsiqueir@redhat.com"}' http://localhost:8080/time/services/user
curl -H "Content-Type: application/json" -d '{"firstName":"Brad","lastName":"Maxwell","email":"bmaxwell@redhat.com"}' http://localhost:8080/time/services/user
echo
echo -n add an action...
curl -H "Content-Type: application/json" -d '{"name":"Case 12345678"}' http://localhost:8080/time/services/action
curl -H "Content-Type: application/json" -d '{"name":"Case 45678901"}' http://localhost:8080/time/services/action
curl -H "Content-Type: application/json" -d '{"name":"Read Reddit"}' http://localhost:8080/time/services/action
curl -H "Content-Type: application/json" -d '{"name":"Meditate"}' http://localhost:8080/time/services/action
echo
echo -n add a time entry...
curl -H "Content-Type: application/json" -d '{"user":{"id":"1"},"action":{"id":"4"}}' http://localhost:8080/time/services/entry
curl -H "Content-Type: application/json" -d '{"user":{"id":"2"},"action":{"id":"5"}}' http://localhost:8080/time/services/entry
curl -H "Content-Type: application/json" -d '{"user":{"id":"3"},"action":{"id":"6"}}' http://localhost:8080/time/services/entry
curl -H "Content-Type: application/json" -d '{"user":{"id":"1"},"action":{"id":"5"}}' http://localhost:8080/time/services/entry
echo
echo -n get a time entry...
curl -H "Accept: application/json" http://localhost:8080/time/services/entry/8
echo
echo -n get a user\'s active entry...
curl -H "Accept: application/json" http://localhost:8080/time/services/user/1/active
echo
echo -n stop a user\'s entry...
curl -X PUT -H "Accept: application/json" http://localhost:8080/time/services/user/1/stop
echo
curl -H "Accept: application/json" http://localhost:8080/time/services/user/1/active
