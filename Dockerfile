FROM openjdk:17
 ADD target/searchservice-0.0.1-SNAPSHOT.jar SearchService.jar
 EXPOSE 8080
ENTRYPOINT ["java","-jar","SearchService.jar"]