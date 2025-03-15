# Customer Finance API

## 📌 Описание
Customer Finance API — это RESTful сервис для управления банковскими операциями клиентов.

## 🔑 Аутентификация

- **`POST /api/public/auth/login`** → Вход в систему (JWT)
- **`POST /api/public/auth/refresh`** → Обновление Access Token
- **`POST /api/public/auth/logout`** → Выход (удаление Refresh Token)

## 👤 Управление пользователями

- **`GET /api/private/users`** → Получить список пользователей
- **`POST /api/private/users`** → Создать нового пользователя
- **`GET /api/private/users/{id}`** → Получить информацию о пользователе
- **`PUT /api/private/users/{id}`** → Обновить данные пользователя
- **`DELETE /api/private/users/{id}`** → Удалить пользователя

## 💰 Управление клиентами

- **`GET /api/private/customers`** → Получить список клиентов
- **`POST /api/private/customers`** → Создать нового клиента
- **`GET /api/private/customers/{id}`** → Получить информацию о клиенте
- **`PUT /api/private/customers/{id}`** → Обновить данные клиента
- **`DELETE /api/private/customers/{id}`** → Удалить клиента

## 💳 Транзакции

- **`POST /api/private/customers/{id}/deposit`** → Пополнение баланса
- **`POST /api/private/customers/{id}/withdrawal`** → Списание средств

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
- **Jeager**  (Трассировка) 

## 🚀 Запуск проекта

1. Склонируйте репозиторий:
   ```bash
   git clone https://github.com/abdulazizsaidov91/customer_finance.git
   
## Ползователь по умолчанию "ADMIN" и создается при запуске проиложение
