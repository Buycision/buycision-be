#!/bin/bash

error_handler() {
  echo ">>> 스크립트 실행 중 에러가 발생했습니다."
  echo ">>> 에러가 발생한 명령: '$BASH_COMMAND'"
  echo ">>> 에러 발생 라인: ${BASH_LINENO[0]}"
  exit 1
}

trap 'error_handler' ERR

echo ">>> Gradle 빌드 시작"
./gradlew clean build

echo ">>> wait-for-it.sh 복사"
SERVICES=("chat-service" "config-service" "discovery-service" "gateway-service" "user-service")
for SERVICE in "${SERVICES[@]}"; do
  cp wait-for-it.sh "./$SERVICE/wait-for-it.sh"
done

echo ">>> 기존 도커 정리"
docker-compose down
docker image prune -f

echo ">>> Docker Compose 기동"
docker-compose up --build -d

echo ">>> 서비스 기동 상태 확인"
docker-compose ps

echo ">>> 배포 완료"