FROM maven:3.8.3-jdk-11 as build
WORKDIR /app/order
COPY . .
RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:11-jre-slim-stretch as final
COPY --from=build /app/order/target/*.jar /app/order/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/order/app.jar"]