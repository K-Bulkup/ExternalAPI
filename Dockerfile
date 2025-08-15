# ---------- Stage 1: Build with Gradle Wrapper ----------
FROM gradle:8.10.2-jdk17 AS build
WORKDIR /app

# 메타 먼저 → 캐시 예열
COPY build.gradle settings.gradle ./
# gradle.properties 쓰면 다음 줄도:
# COPY gradle.properties ./
RUN gradle dependencies --no-daemon || true

# 소스 복사 후 빌드
COPY src ./src
RUN gradle clean build -x test --no-daemon

# ---- Stage 2: Tomcat ----
FROM tomcat:9.0-jdk17-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/build/libs/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh","run"]
