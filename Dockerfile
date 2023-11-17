FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar meu-demo-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/meu-demo-service-0.0.1-SNAPSHOT.jar"]
