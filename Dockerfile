FROM openjdk:11

COPY .. /home/homework

WORKDIR /home/homework

CMD java -jar build/libs/security-0.0.1-SNAPSHOT.jar