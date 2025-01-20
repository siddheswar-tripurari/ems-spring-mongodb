#Base Image
FROM eclipse-temurin:21-jdk-alpine

#Copy Files
COPY .mvn /ems/.mvn/
COPY pom.xml /ems/
COPY mvnw /ems/
COPY src /ems/src/

#COPY target/ /ems/target/

#Set Work Directory
WORKDIR /ems

#Run
CMD ["./mvnw","spring-boot:run"]
#CMD ["java","-jar","target/ems-0.0.1-SNAPSHOT.jar"]

