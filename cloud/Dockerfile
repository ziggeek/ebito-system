FROM openjdk:11

WORKDIR /opt/app

COPY target/*exec.jar app.jar

EXPOSE 8084

CMD [ "java", "-jar", "/opt/app/app.jar" ]