FROM maven:3.8.3-jdk-11 as build
WORKDIR /app/user
COPY . .
RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:11-jre-slim-stretch as final
COPY --from=build /app/user/target/*.jar /app/user/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/user/app.jar"]