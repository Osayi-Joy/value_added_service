FROM openjdk:17
EXPOSE 3700
ARG VERSION
ENV VERSION=$VERSION

WORKDIR /

COPY ./target/billent-backoffice-service-0.0.1-SNAPSHOT.jar billent-backoffice-service.jar

COPY . /


CMD [ "java", "-jar", "-Dspring.profiles.active=dev", "billent-backoffice-service-0.0.1-SNAPSHOT.jar" ]
