FROM openjdk:8-jre-alpine

MAINTAINER Kirill Semenov <kirikx@mail.ru>

WORKDIR /usr/src/myapp

COPY target/testbest-app-0.1.jar /usr/src/myapp

EXPOSE 8080
ENTRYPOINT java -jar ./testbest-app-0.1.jar
