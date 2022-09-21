FROM adoptopenjdk/openjdk8:latest
ADD /target/rs-service.jar rs-service.jar
COPY /target/conf conf
COPY /target/rs-service_lib rs-service_lib
ENTRYPOINT ["java","-jar","/rs-service.jar"]