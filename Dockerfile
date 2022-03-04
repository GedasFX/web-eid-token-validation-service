FROM maven:3 as build

WORKDIR /build

COPY pom.xml .
RUN mvn dependency:resolve-plugins dependency:resolve

COPY . .

RUN mvn clean package

FROM openjdk:8-jre-alpine
COPY --from=build /build/target/web-eid-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]