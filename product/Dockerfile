FROM maven:3.8.3-jdk-11 as build
WORKDIR /app/product
COPY . .
RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:11-jre-slim-stretch as final
COPY --from=build /app/product/target/*.jar /app/product/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/product/app.jar"]