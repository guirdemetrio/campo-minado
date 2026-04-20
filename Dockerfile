FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY campo-minado/pom.xml .
COPY campo-minado/src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/campo-minado-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar app.jar; sleep 30"]
