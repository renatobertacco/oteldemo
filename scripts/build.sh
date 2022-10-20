#!/bin/bash

echo "--------------------------------"
echo "    Download Zipkin"
echo "--------------------------------"

if [ -f "zipkin.jar" ]; then
    echo "zipkin.jar already downloaded"
else
    curl -sSL https://zipkin.io/quickstart.sh | bash -s
fi

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

if [ -f "opentelemetry-javaagent-1.19.0.jar" ]; then
    echo "opentelemetry-javaagent-1.19.0.jar already downloaded"
else
    curl -o opentelemetry-javaagent-1.19.0.jar https://repo1.maven.org/maven2/io/opentelemetry/javaagent/opentelemetry-javaagent/1.19.0/opentelemetry-javaagent-1.19.0.jar
fi

if [ -f "opentelemetry-extension-trace-propagators-1.19.0.jar" ]; then
    echo "opentelemetry-extension-trace-propagators-1.19.0.jar already downloaded"
else
    curl -o opentelemetry-extension-trace-propagators-1.19.0.jar https://repo1.maven.org/maven2/io/opentelemetry/opentelemetry-extension-trace-propagators/1.19.0/opentelemetry-extension-trace-propagators-1.19.0.jar
fi

