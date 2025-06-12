FROM maven:3.9.6-amazoncorretto-8-debian-bookworm AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM tomcat:10-jre8-openjdk-buster

RUN apt-get update 
RUN apt-get install -y openssh-server
RUN mkdir /var/run/sshd


COPY --from=build /build/target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

COPY --from=build /build/target/ROOT /usr/local/tomcat/webapps/ROOT

# SSH 
COPY init.sh /init.sh
RUN chmod +x /init.sh

EXPOSE 8080 22
CMD ["/bin/bash", "/init.sh"]