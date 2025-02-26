FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY /Task_springboot/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8081
