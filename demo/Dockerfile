FROM openjdk:11-jdk-slim


COPY ${JAR_HOME:-build/libs}/demo*.jar /opt/system/

EXPOSE 8080 8080

WORKDIR /opt/system
CMD java -jar demo-*.jar -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom