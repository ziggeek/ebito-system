# Ebito — Система генерации счетов

## Задача

Реализовать систему, которая будет получать данные о начислениях абоненту из разных источников и на основе полученных
данных генерировать пользователю один счет в виде pdf-файла, содержащего все начисления

## Архитектура системы

![Изображение: Архитектура системы](https://i.ibb.co/fptyc0z/ebito-architecture.jpg)

## Стек технологий

- Java 11
- Spring Framework
- Maven
- MinIO
- PostgreSQL
- RabbitMQ

## Локальный запуск приложения

1. Запустить RabbitMQ, MinIO, PostgreSQL с помощью `docker-compose.yaml`:
    ```
   docker compose up
    ```
2. Запустить микросервисы с профилем `local`:
    - [orchestrator](./orchestrator/README.md)
    - [data-source](./data-source/README.md)
    - [data-aggregator](./data-aggregator/README.md)
    - [document-generator](./document-generator/README.md)
    - [cloud](./cloud/README.md)

## Авторы

- Артур Мурадов
- Ренат Скосарев
- Ильяс Кучукбаев
- Илья Попов
