# Используем образ с Java 17
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем JAR-файл приложения в контейнер
COPY target/customer-finance.jar app.jar

# Открываем порт 8081
EXPOSE 8081

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
