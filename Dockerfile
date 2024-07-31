FROM eclipse-temurin:17

LABEL maintainer="jackmu@umich.edu"

WORKDIR /app

COPY target/s3-file-uploader.jar /app/s3-file-uploader.jar

ENTRYPOINT ["java", "-jar", "s3-file-uploader.jar"]