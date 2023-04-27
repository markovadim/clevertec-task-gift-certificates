FROM openjdk:17
COPY ./build/libs/gift-certificates-1.0-SNAPSHOT.war certificate.war
ENTRYPOINT ["java", "-jar", "certificate.war"]