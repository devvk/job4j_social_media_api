# Social Media API

RESTful API для социальной медиа платформы.

Проект позволяет пользователям регистрироваться, авторизоваться, создавать посты, подписываться на других пользователей, добавлять их в друзья, обмениваться сообщениями и получать ленту активности.

## Стек технологий

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- Liquibase
- Maven
- Swagger / OpenAPI
- JUnit 5
- Mockito
- Checkstyle
- GitHub Actions

## Основные возможности

### Аутентификация и авторизация

- Регистрация пользователя
- Авторизация пользователя
- Хэширование паролей
- JWT-аутентификация
- Защита приватных эндпоинтов

### Посты

- Создание поста
- Получение постов
- Редактирование поста
- Удаление поста
- Загрузка изображений

### Друзья и подписки

- Отправка заявки в друзья
- Принятие заявки
- Отклонение заявки
- Удаление из друзей
- Подписка на пользователя
- Отписка от пользователя

### Сообщения

- Отправка сообщений друзьям
- Получение истории переписки

### Лента активности

- Получение постов пользователей, на которых оформлена подписка
- Сортировка по дате создания
- Пагинация

### Обработка ошибок

- Валидация входных данных
- Единый формат ошибок
- Обработка ошибок авторизации
- Обработка бизнес-ошибок

### Документация API

- Swagger/OpenAPI

---

## Технологии

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- Liquibase
- Swagger/OpenAPI
- JUnit 5
- Mockito
- Checkstyle
- GitHub Actions


Структура проекта:

```text
src
├── main
│   ├── java
│   │   └── ru.job4j.socialmedia
│   │       ├── controller
│   │       ├── service
│   │       ├── repository
│   │       ├── model
│   │       ├── dto
│   │       ├── mapper
│   │       ├── security
│   │       └── exception
│   └── resources
│       ├── application.properties
│       └── db
│           └── changelog
└── test
```

## Основные сущности

### User

Пользователь системы.

### Post

Публикация пользователя.

### Image

Изображение поста.

### FriendRequest

Заявка в друзья.

### Subscription

Подписка на пользователя.

### Message

Личное сообщение между друзьями.


## Запуск проекта

### Требования

- Java 21+
- Maven 3.9+
- PostgreSQL 15+

### Сборка

```bash
mvn clean install
```

### Запуск

```bash
mvn spring-boot:run
```

## Тестирование

Запуск тестов:

```bash
mvn test
```

## Проверка стиля кода

```bash
mvn checkstyle:check
```

## GitHub Actions

Для проекта настроен CI-пайплайн.

При каждом Push и Pull Request выполняются:

- Сборка проекта
- Запуск тестов
- Проверка Checkstyle

Бейдж статуса сборки:

```md
![Build](https://github.com/devvk/job4j_social_media_api/actions/workflows/maven.yml/badge.svg)
```

## Документация API

После запуска приложения Swagger будет доступен по адресу:

```text
http://localhost:8080/swagger-ui/index.html
```
