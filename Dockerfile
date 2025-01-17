#Base Image
FROM eclipse-temurin:21-jdk-alpine

#Copy Files
COPY .mvn /ems/.mvn/
COPY pom.xml /ems/
COPY mvnw /ems/
COPY src /ems/src/

#Set Work Directory
WORKDIR /ems

#Run
CMD ["./mvnw","spring-boot:run"]

