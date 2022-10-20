
# Introduction

OpenTelemetry (otel) allows enabling traces, trace propagation and metrics in an existing Java app without requiring code changes. For more information see [opentelemetry java instrumentation](https://github.com/open-telemetry/opentelemetry-java-instrumentation).

You can find latest OpenTelemetry jar libraries version on maven central

- [opentelemetry-javaagent](https://mvnrepository.com/artifact/io.opentelemetry.javaagent/opentelemetry-javaagent)
- [opentelemetry-extension-trace-propagators](https://mvnrepository.com/artifact/io.opentelemetry/opentelemetry-extension-trace-propagators)


For using OpenTelemetry in your Java application you have only to enable the instrumentation agent using the  _-javaagent_  flag to the JVM. An example for OpenTelemetry 1.19.0 is:​

```
java -javaagent:/path/to/opentelemetry-javaagent-1.19.0.jar​
     -Dotel.traces.exporter=zipkin ​
     -Dotel.javaagent.extensions=/path/to/opentelemetry-extension-trace-propagators-1.19.0.jar  
     -Dotel.exporter.zipkin.endpoint=http://zipkin_hostname:9411/api/v2/spans ​
     -Dotel.metrics.exporter=none
     -Dotel.propagation.enabled=true 
     -Dotel.propagators=b3multi 
     -Dotel.resource.attributes=service.name=bikeservice,service.namespace=demo 

     <...your application parameters..> 
```

See the full list of properties [here](https://github.com/open-telemetry/opentelemetry-java/blob/main/sdk-extensions/autoconfigure/README.md)


## Zipkin

[Zipkin](https://github.com/openzipkin/zipkin) is a distributed tracing system. It helps gather timing data needed to troubleshoot latency problems in service architectures.

Download Zipkin:

```
curl -sSL https://zipkin.io/quickstart.sh | bash -s
```

Run Zipkin:

```
java -jar zipkin.jar
```

Open Zipkin on your browser at url:

```
http://localhost:9411/
```

# How to build our demo

If you do not have it yet then install [maven](https://maven.apache.org/) then move on bike.rental.demo folder and run

```
mvn clean package
```

# Scripts

The folder _/scripts_ contains some facilities for helping to build and run our demo:

## build.sh

- Download zipkin in to the scripts folder
- Build demo application and copy the jar file to scripts folder
- Download OpenTelemetry jar files in to the scripts folder

## run.sh

- Run zipkin
- Run bike app on default port (8080) with otel java agent
- Run client app on default port (8081) with otel java agent
- Run rental app on default port (8082) with otel java agent

Before running the script make sure the OpenTelemetry jars are in the same folder:
- [opentelemetry-javaagent](https://mvnrepository.com/artifact/io.opentelemetry.javaagent/opentelemetry-javaagent)
- [opentelemetry-extension-trace-propagators](https://mvnrepository.com/artifact/io.opentelemetry/opentelemetry-extension-trace-propagators)

## stop.sh

- Stop zipkin
- Stop bike app
- Stop client app
- Stop rental app

# Bike App

## Request examples:

List bikes

```
curl -uuser:password http://localhost:8080/rest/bike/bikes | json_pp
curl -uuser:password 'http://localhost:8080/rest/bike/bikes?size=XL&model=Canyon' | json_pp
curl -uuser:password http://localhost:8080/rest/bike/bikes/1 | json_pp
curl -uuser:password http://localhost:8080/rest/bike/bikes/listBooked | json_pp
curl -uuser:password http://localhost:8080/rest/bike/bikes/listAvailables | json_pp
curl -uuser:password http://localhost:8080/rest/bike/bikes/listAvailables?size=XL | json_pp
curl -uuser:password 'http://localhost:8080/rest/bike/bikes/listAvailables?size=S&model=Canyon' | json_pp
```

Set a client

```
curl -XPUT -uuser:password http://localhost:8080/rest/bike/bikes/1/rentBy/1
```

Remove a client

```
curl -XPUT -uuser:password http://localhost:8080/rest/bike/bikes/1/rentBy
```

Create a new bike

```
curl -XPOST -uuser:password http://localhost:8080/rest/bike/bikes  -H 'Content-Type: application/json' -d '
{
  "model" : "Stereo One77",
  "rate" : 70,
  "size" : "XL"
}'
```

Update a bike

```
curl -XPUT -uuser:password http://localhost:8080/rest/bike/bikes/78  -H 'Content-Type: application/json' -d '
{
  "model" : "Stereo One77 Pro29",
  "rate" : 70,
  "size" : "XL"
}'
```

Delete a bike

```
curl -XDELETE -uuser:password http://localhost:8080/rest/bike/bikes/78
```

# Client App

## Request examples:

List clients

```
curl -XGET -uuser:password http://localhost:8081/rest/client/clients | json_pp
curl -XGET -uuser:password http://localhost:8081/rest/client/clients?name=Paolo | json_pp
```

Create a new client

```
curl -XPOST -uuser:password http://localhost:8081/rest/client/clients -H 'Content-Type: application/json' -d '
{
  "name": "John Gladstone",
  "idCard": "123456789",
  "email": "john.gladstone@example.org",
  "phone":"‎+13324558954"
}'
```

Create a new client

```
curl -XPUT -uuser:password http://localhost:8081/rest/client/clients/6 -H 'Content-Type: application/json' -d '
{
  "name": "John Gladstone",
  "idCard": "123456789",
  "email": "john.gladstone@gmail.org",
  "phone":"‎+13324558954"
}'
```

Delete a client

```
curl -XDELETE -uuser:password http://localhost:8081/rest/client/clients/6
```

# Rental App

## Request examples:

List bikes available

```
curl -uuser:password http://localhost:8082/rest/rental/listAvailables | json_pp
curl -uuser:password 'http://localhost:8082/rest/rental/listAvailables?size=L&model=stereo' | json_pp
```

List bikes rented

```
curl -uuser:password http://localhost:8082/rest/rental/listRented  | json_pp
```

Start and stop a rental

```
curl -XPUT -uuser:password 'http://localhost:8082/rest/rental/startRental?bikeId=1&clientId=1' | json_pp
curl -XPUT -uuser:password http://localhost:8082/rest/rental/stopRental?bikeId=1 | json_pp
```

