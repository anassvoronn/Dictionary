# Используем официальный образ OpenJDK
FROM eclipse-temurin:17-jdk-jammy

# Создаем рабочую директорию
WORKDIR /app

# Копируем JAR-файл в контейнер
COPY target/Dictionary-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт, который использует приложение
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]