#!/bin/bash

echo "stop zipkin"
kill $(lsof -t -i:9411) 
echo "stop bike.app"
kill $(lsof -t -i:8080) 
echo "stop client.app"
kill $(lsof -t -i:8081) 
echo "stop rental.app"
kill $(lsof -t -i:8082) 
