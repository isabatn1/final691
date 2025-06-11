# Build aşaması
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Run aşaması
FROM tomcat:10-jre8-openjdk-buster

# SSH kurulumu
RUN apt-get update && \
    apt-get install -y openssh-server && \
    mkdir /var/run/sshd

# init.sh kopyalanır
COPY init.sh /init.sh
RUN chmod +x /init.sh

# WAR ve klasör kopyalanır
COPY --from=build /build/target/ROOT.war /usr/local/tomcat/webapps/ROOT.war
COPY --from=build /build/target/ROOT /usr/local/tomcat/webapps/ROOT

# Portlar
EXPOSE 8080 22

# init.sh çalıştır
CMD ["/init.sh"]
