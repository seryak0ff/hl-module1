# Стадия сборки
FROM gradle:8.5-jdk21-alpine AS builder

WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
RUN gradle --no-daemon dependencies

COPY src ./src
RUN gradle bootJar --parallel --no-daemon

# Финальный образ
FROM eclipse-temurin:21-jre-alpine
# Запуск от непривелегированного пользователя
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]