FROM openjdk:17-jdk-slim as builder

# JAR 빌드 산출물 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app/config-service.jar

# 애플리케이션 실행
CMD ["java", "-jar", "/app/config-service.jar"]