FROM adoptopenjdk/openjdk11:latest

WORKDIR /app

ADD build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar"]
CMD ["app.jar"]
