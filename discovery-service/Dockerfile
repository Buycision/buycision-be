FROM openjdk:17-jdk-slim as builder

# JAR 빌드 산출물 복사 (Gradle 빌드 결과물 가정)
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app/discovery-service.jar

# wait-for-it.sh 스크립트 복사
COPY ../wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# 실제 CMD에서 wait-for-it.sh를 먼저 실행하도록 설정
CMD ["/wait-for-it.sh", "config-service:8888", "--timeout=60", "--strict", "--", "java", "-jar", "/app/discovery-service.jar"]