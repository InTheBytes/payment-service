FROM openjdk:17
 ADD target/paymentservice-0.0.1-SNAPSHOT.jar PaymentService.jar
 EXPOSE 8080
ENTRYPOINT ["java","-jar","PaymentService.jar"]