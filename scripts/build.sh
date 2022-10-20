#!/bin/bash

echo "--------------------------------"
echo "    Download Zipkin"
echo "--------------------------------"
curl -sSL https://zipkin.io/quickstart.sh | bash -s

echo "--------------------------------"
echo "    Build Demo App"
echo "--------------------------------"
mvn -f ../ clean package

echo "--------------------------------"
echo "Copy App jars"
echo "--------------------------------"
echo "Copy bike.app"
cp ../bike.app/target/bike.app*.jar .
echo "Copy client.app"
cp ../client.app/target/client.app*.jar .
echo "Copy rental.app"
cp ../rental.app/target/rental.app*.jar .

echo "--------------------------------"
echo "    Download OpenTelemetry"
echo "--------------------------------"
curl -o opentelemetry-javaagent-1.19.0.jar https://repo1.maven.org/maven2/io/opentelemetry/javaagent/opentelemetry-javaagent/1.19.0/opentelemetry-javaagent-1.19.0.jar
curl -o opentelemetry-extension-trace-propagators-1.19.0.jar https://repo1.maven.org/maven2/io/opentelemetry/opentelemetry-extension-trace-propagators/1.19.0/opentelemetry-extension-trace-propagators-1.19.0.jar
