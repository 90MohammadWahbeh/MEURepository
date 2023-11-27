FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
<<<<<<< HEAD
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
=======
COPY target/*.jar meu-demo-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/meu-demo-service-0.0.1-SNAPSHOT.jar"]
>>>>>>> bff3921aecab783bb9a362c6f92c9bcc0581bf02
