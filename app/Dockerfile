FROM eclipse-temurin:21
RUN mkdir /app
COPY target/app-*.jar /app
EXPOSE 8080

CMD java -jar /app/app-*.jar
