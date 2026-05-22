# ProductCatalog

Веб-приложение на Java Spring Boot для просмотра каталога товаров и отправки обращений администратору.

---

# Функциональность

## Реализовано

- регистрация пользователей;
- авторизация через Spring Security;
- BCrypt-хеширование паролей;
- каталог товаров;
- поиск товаров;
- просмотр детальной информации о товаре;
- форма обратной связи;
- отправка email через SMTP;
- история обращений пользователя;
- PostgreSQL;
- Liquibase миграции;
- unit и integration тесты.

---

# Технологии

- Java 21
- Spring Boot 3
- Spring MVC
- Spring Security
- Spring Data JPA
- PostgreSQL
- Liquibase
- Thymeleaf
- Maven
- JUnit 5

---

# Требования

Для запуска необходимы:

- JDK 21
- Maven 3.9+
- PostgreSQL 14+
- SMTP-аккаунт

---

# Настройка PostgreSQL

Создать базу данных:

```sql
CREATE DATABASE catalog_feedback_db;
```

---

# Настройка переменных окружения

## Windows CMD

```cmd
set DB_USERNAME=postgres
set DB_PASSWORD=your_password

set MAIL_USERNAME=your_email@gmail.com
set MAIL_PASSWORD=your_app_password

set ADMIN_EMAIL=your_email@gmail.com
```

## Проверка переменных

```cmd
echo %DB_USERNAME%
echo %MAIL_USERNAME%
```

---

# Настройка Gmail SMTP

1. Включить двухфакторную аутентификацию Google.
2. Открыть:
   - Google Account
   - Security
   - App Passwords
3. Создать App Password.
4. Использовать его как `MAIL_PASSWORD`.

---

# Сборка проекта

```bash
mvn clean package
```

После сборки jar-файл появится в:

```text
target/ProductCatalog-1.0-SNAPSHOT.jar
```

---

# Запуск проекта

## Через Maven

```bash
mvn spring-boot:run
```

## Через jar

```bash
java -jar target/ProductCatalog-1.0-SNAPSHOT.jar
```

## Через run.bat

```text
run.bat
```

---

# Основные страницы

## Главная

```text
http://localhost:8080
```

## Каталог товаров

```text
http://localhost:8080/products
```

## Регистрация

```text
http://localhost:8080/register
```

## Вход

```text
http://localhost:8080/login
```

## История обращений

```text
http://localhost:8080/feedback/history
```

---

# Тестирование

Запуск тестов:

```bash
mvn test
```

В проекте реализованы:

- unit tests;
- integration tests.

---

# Архитектура

Проект реализован по MVC-архитектуре:

- Controllers — обработка HTTP-запросов;
- Services — бизнес-логика;
- Repositories — работа с БД;
- Entities — JPA-сущности;
- Templates — HTML-страницы Thymeleaf.

---

# Безопасность

- Spring Security;
- session-based authentication;
- CSRF protection;
- BCrypt password hashing;
- environment variables для секретов.

---

# Автор

Студент группы Б23-901, Суббота Степан Анатольевич.
