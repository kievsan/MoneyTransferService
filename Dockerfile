FROM openjdk:21

VOLUME /tmp
EXPOSE 8088

ADD /target/spring_boot_rest_auth_service-0.0.1-SNAPSHOT.jar rest_transfer.jar

ENTRYPOINT ["java","-jar","/rest_transfer.jar"]
