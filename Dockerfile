FROM openjdk:17
WORKDIR /
COPY ./config /config/
COPY ./keys /keys/
COPY ./src /src/
COPY ./target/*.jar /
EXPOSE 3700
ENTRYPOINT ["java", "-jar", "billent-backoffice-service-0.0.1-SNAPSHOT.jar"]
