# Customer Finance API

## 📌 Описание
Customer Finance API — это RESTful сервис для управления банковскими операциями клиентов.

## 🔑 Аутентификация

- **`POST /api/public/authenticate`** → Вход в систему (JWT)
- **`POST /api/public/refresh`** → Обновление Access Token
- **`POST /api/public/logout`** → Выход (удаление Refresh Token)

## 👤 Управление пользователями

- **`GET /api/private/users`** → Получить список пользователей
- **`POST /api/private/users/create`** → Создать нового пользователя
- **`GET /api/private/users/{id}`** → Получить информацию о пользователе
- **`PUT /api/private/users/update/{id}`** → Обновить данные пользователя
- **`DELETE /api/private/users/{id}`** → Удалить пользователя

## 💰 Управление клиентами

- **`GET /api/private/customers`** → Получить список клиентов
- **`POST /api/private/customers`** → Создать нового клиента
- **`GET /api/private/customers/{id}`** → Получить информацию о клиенте
- **`PUT /api/private/customers/{id}`** → Обновить данные клиента
- **`DELETE /api/private/customers/{id}`** → Удалить клиента

## 💳 Транзакции

- **`POST /api/private/customers/{id}/credit`** → Пополнение баланса
- **`POST /api/private/customers/{id}/debit`** → Списание средств

## 🔐 Роли и доступы

| Роль    | Доступ |
|---------|--------|
| `ADMIN` | Полный доступ ко всем операциям |
| `CASHIER` | Управление клиентами и транзакциями |
| `OPERATOR` | Только просмотр информации о клиентах |

## 🛠 Технологии

- **Spring Boot** (Security, Data JPA)
- **JWT** (Аутентификация и авторизация)
- **PostgreSQL** (База данных)
- **Liquibase** (Миграции)
- **Swagger** (Документация API) (http://serverIP:8081/customer_finance/swagger-ui/index.html)
- **Jeager**  (Трассировка) (http://serverIP:16686/)

## 🚀 Запуск проекта

1. Склонируйте репозиторий:
   ```bash
   git clone https://github.com/abdulazizsaidov91/customer_finance.git
 
## Ползователь по умолчанию "admin" и создается при запуске проиложение

## Запустите команду "docker-compose up -d --build" и все создастса. Парол для получение токена "admin" 
