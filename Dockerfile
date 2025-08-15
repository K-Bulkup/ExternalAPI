# ---------- Stage 1: Build with Gradle Wrapper ----------
FROM gradle:8.10.2-jdk17 AS build
WORKDIR /app

# 프로젝트 메타/래퍼 먼저 복사 (캐시 최적화)
COPY build.gradle settings.gradle ./
# COPY gradlew ./
# COPY gradle ./gradle

# COPY gradle.properties ./
RUN gradle dependencies --no-daemon || true

#RUN sed -i 's/\r$//' ./gradlew && chmod +x ./gradlew

# 권한 및 의존 예열
RUN ./gradlew dependencies --no-daemon

# 소스 복사
COPY src ./src

# 빌드 (테스트 제외는 선택)
RUN ./gradlew clean build -x test --no-daemon

# ---------- Stage 2: Runtime (Tomcat 9, JDK 17) ----------
FROM tomcat:9.0-jdk17-temurin
# 불필요한 기본 앱 제거
RUN rm -rf /usr/local/tomcat/webapps/*

# WAR → ROOT로 배포 (Tomcat이 자동으로 풀어줌)
COPY --from=build /app/build/libs/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
