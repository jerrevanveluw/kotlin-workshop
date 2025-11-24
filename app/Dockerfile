FROM eclipse-temurin:25-alpine
RUN mkdir /app
COPY target/app-*.jar /app
EXPOSE 8080

CMD java -jar /app/app-*.jar
