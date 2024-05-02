FROM openjdk:17
VOLUME /tmp
EXPOSE 8181
ARG JAR_FILE=target/EventForge-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]