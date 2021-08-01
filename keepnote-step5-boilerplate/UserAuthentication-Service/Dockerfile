FROM openjdk:11-jre-slim
WORKDIR usr/src
ADD ./target/UserAuthentication-Service-0.0.1-SNAPSHOT.jar /usr/src/UserAuthentication-Service-0.0.1-SNAPSHOT.jar
ENV MYSQL_URI MYSQL_URI:jdbc:mysql://localhost:3306/mydemo
ENV MYSQL_USERNAME root
ENV MYSQL_PASSWORD password
ENTRYPOINT ["java", "-jar", "/usr/src/UserAuthentication-Service-0.0.1-SNAPSHOT.jar"]