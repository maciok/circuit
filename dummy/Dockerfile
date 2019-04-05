FROM openjdk:11-jdk-slim


COPY ${JAR_HOME:-build/libs}/dummy*.jar /opt/system/


EXPOSE 8180 8180

WORKDIR /opt/system
CMD java -jar dummy-*.jar -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom