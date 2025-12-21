FROM eclipse-temurin:17-jdk
EXPOSE 8090
WORKDIR /app

COPY target/*.war app.war
ENTRYPOINT ["java","-jar","app.war"]
