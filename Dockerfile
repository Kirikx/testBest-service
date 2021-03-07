FROM openjdk:8-jre-alpine

MAINTAINER Kirill Semenov <kirikx@mail.ru>

WORKDIR /usr/src/myapp

COPY ./testbest-app /usr/src/myapp

EXPOSE 8080
ENTRYPOINT java -jar ./target/testbest-app.jar
