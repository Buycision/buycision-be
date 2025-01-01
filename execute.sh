#!/bin/bash

error_handler() {
  echo ">>> 스크립트 실행 중 에러가 발생했습니다."
  echo ">>> 에러가 발생한 명령: '$BASH_COMMAND'"
  echo ">>> 에러 발생 라인: ${BASH_LINENO[0]}"
  exit 1
}

trap 'error_handler' ERR

echo ">>> Gradle 빌드 시작"

echo ">>> 서비스 준비 중..."
docker image prune -f

PORT=8888
PID=$(lsof -ti :$PORT || true)

if [ -n "$PID" ]; then
  echo "포트 $PORT 를 점유 중인 프로세스(PID: $PID)를 종료합니다."
  kill -9 $PID
  echo "포트 $PORT 의 프로세스 종료 완료."
else
  echo "포트 $PORT 를 점유 중인 프로세스가 없습니다. 다음 단계로 진행합니다."
fi

echo ">>> Config Server 빌드"
./gradlew clean :config-service:build

echo ">>> Config Server 실행"
java -jar ./config-service/build/libs/config-*.jar &

# Config Server PID 저장
CONFIG_PID=$!

echo ">>> Config Server 준비 중..."
sleep 2

echo ">>> Config Server 준비 완료"

echo ">>> 나머지 서비스 빌드 시작"
./gradlew clean build

echo ">>> Config Server 종료"
kill $CONFIG_PID
wait $CONFIG_PID 2>/dev/null || true

echo ">>> Config Server 종료 완료"

echo ">>> Docker Compose 시작"

echo ">>> Config Service 실행 시작"
docker-compose down && docker-compose up --build -d config-service

echo ">>> Config Service 준비 중..."
sleep 2

echo ">>> 나머지 서비스 실행 시작"
docker-compose up --build -d discovery-service gateway-service user-service bus-service

echo ">>> 모든 서비스 실행 완료"
docker-compose ps

echo ">>> 배포 완료"