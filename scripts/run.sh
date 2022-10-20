#!/bin/bash

java -jar zipkin.jar &

# bike service
java -javaagent:opentelemetry-javaagent-1.19.0.jar \
     -Dotel.javaagent.extensions=opentelemetry-extension-trace-propagators-1.19.0.jar \
     -Dotel.propagation.enabled=true \
     -Dotel.propagators=b3multi \
     -Dotel.resource.attributes=service.name=bikeservice,service.namespace=demo \
     -Dotel.traces.exporter=zipkin \
     -Dotel.exporter.zipkin.endpoint=http://localhost:9411/api/v2/spans \
     -Dotel.metrics.exporter=none \
     -jar bike.app-0.0.1-SNAPSHOT.jar &
# client service
java -javaagent:opentelemetry-javaagent-1.19.0.jar \
     -Dotel.javaagent.extensions=opentelemetry-extension-trace-propagators-1.19.0.jar \
     -Dotel.propagation.enabled=true \
     -Dotel.propagators=b3multi \
     -Dotel.resource.attributes=service.name=clientservice,service.namespace=demo \
     -Dotel.traces.exporter=zipkin \
     -Dotel.exporter.zipkin.endpoint=http://localhost:9411/api/v2/spans \
     -Dotel.metrics.exporter=none \
     -jar client.app-0.0.1-SNAPSHOT.jar &
# rental service
java -javaagent:opentelemetry-javaagent-1.19.0.jar \
     -Dotel.javaagent.extensions=opentelemetry-extension-trace-propagators-1.19.0.jar \
     -Dotel.propagation.enabled=true \
     -Dotel.propagators=b3multi \
     -Dotel.resource.attributes=service.name=rentalservice,service.namespace=demo \
     -Dotel.traces.exporter=zipkin \
     -Dotel.exporter.zipkin.endpoint=http://localhost:9411/api/v2/spans \
     -Dotel.metrics.exporter=none \
     -jar rental.app-0.0.1-SNAPSHOT.jar &

while ! nc -z localhost 8082; do   
  sleep 1 
done
echo "
  _____  ______ __  __  ____       _____  ______          _______     __
 |  __ \|  ____|  \/  |/ __ \     |  __ \|  ____|   /\   |  __ \ \   / /
 | |  | | |__  | \  / | |  | |    | |__) | |__     /  \  | |  | \ \_/ / 
 | |  | |  __| | |\/| | |  | |    |  _  /|  __|   / /\ \ | |  | |\   /  
 | |__| | |____| |  | | |__| |    | | \ \| |____ / ____ \| |__| | | |   
 |_____/|______|_|  |_|\____/     |_|  \_\______/_/    \_\_____/  |_|   
                                                                        
                                                                        
"
echo "Zipkin url is:    http://localhost:9411/"


