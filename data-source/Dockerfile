FROM openjdk:11

WORKDIR /opt/app

COPY target/*exec.jar app.jar

EXPOSE 8085

CMD [ "java", "-jar", "/opt/app/app.jar" ]