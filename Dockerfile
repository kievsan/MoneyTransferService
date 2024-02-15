FROM openjdk:21

VOLUME /tmp
EXPOSE 8088

ADD /target/money_transfer_service-0.0.1-SNAPSHOT.jar rest_transfer.jar

ENTRYPOINT ["java","-jar","/rest_transfer.jar"]
