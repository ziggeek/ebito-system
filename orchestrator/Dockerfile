FROM openjdk:11

WORKDIR /opt/app

COPY target/*exec.jar app.jar

EXPOSE 8081

CMD [ "java", "-jar", "-Dspring.profiles.active=yacloud", "/opt/app/app.jar"]