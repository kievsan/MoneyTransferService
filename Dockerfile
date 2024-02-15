FROM openjdk:21

VOLUME /tmp
EXPOSE 5500

ADD /target/money_transfer_service-0.0.1-SNAPSHOT.jar rest_transfer.jar

ENTRYPOINT ["java","-jar","/rest_transfer.jar"]
