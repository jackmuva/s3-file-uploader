FROM eclipse-temurin:17

LABEL maintainer="jackmu@umich.edu"

WORKDIR /app

COPY target/trtlmail-rest.jar /app/trtlmail-rest.jar

ENTRYPOINT ["java", "-jar", "trtlmail-rest.jar"]