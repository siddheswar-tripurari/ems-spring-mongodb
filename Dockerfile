FROM eclipse-temurin:21-jdk-alpine

WORKDIR /ems

COPY .mvn /ems/.mvn/
COPY pom.xml /ems/
COPY mvnw /ems/

COPY src /ems/src/

CMD ["./mvnw","spring-boot:run"]

