FROM registry.cn-hangzhou.aliyuncs.com/mxzero/qianmeng-hz:jdk-17

WORKDIR /app

COPY target/security-simple-1.0-SNAPSHOT.jar security-simple-1.0-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "security-simple-1.0-SNAPSHOT.jar"]