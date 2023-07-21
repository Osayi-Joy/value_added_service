FROM maven:3.9-eclipse-temurin-17
WORKDIR /
COPY . /
RUN cd / && mvn test -Dspring.profiles.active=test
RUN cd / && mvn clean package spring-boot:repackage -DskipTests
RUN cp /target/*.jar /
EXPOSE 3700
ENTRYPOINT ["java", "-jar", "billent-backoffice-service-0.0.1-SNAPSHOT.jar"]
