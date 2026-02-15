FROM docker.io/openjdk:21-ea-34
WORKDIR /app
COPY ./target/dayoff-*.jar dayoff.jar
CMD java -jar dayoff.jar
EXPOSE 8080