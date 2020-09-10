FROM adoptopenjdk/openjdk11:latest

WORKDIR /app

ADD build/libs/*.jar app.jar
ADD info.data info.data

ENTRYPOINT ["java", "-jar"]
CMD ["app.jar"]
