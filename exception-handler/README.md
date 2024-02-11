# exception-handler

## Автор:<br>
Мурадов Артур ([ziggeek](https://github.com/ziggeek))<br>

##Подключение:

`<dependency>

    <groupId>com.ebito</groupId>
    <artifactId>exception-handler</artifactId>
    <version>${exception-handler.version}</version>
</dependency>`

##Описание:

Библиотека реализует базовую обработку ошибок и предоставляет список основных классов-исключений
При подключении библиотеки обработчик имеет наивысший приоритет при инициализации.

##Коды ошибок:

При ответе сервиса поле errorCode имеет целочисленное положительное значение которое описывает детали ошибки. Имеет следующую структуру:
- 105 неизвестная ошибка
- с 1000 до 1999 валидация и некорректные данные переданные в систему
- с 2000 до 3999 ошибки в ходе исполнения программы и несвязанные с бизнес-частью
- с 4000 до 5999 бизнес-ошибки
- c 6000 до 8499 любые взаимодествия со сторонними сервисами
- c 8500 до 8999 любые взаимодествия со сторонними сервисами, которые можно пытаться повторить.

##Примеры ответов сервиса при возникновении ошибки:

```json
{
  "application": "ebito-application-api", 
  "endpoint": "/api/v1/...",
  "errorCode": 2000,
  "messages": {
    "rus": "Внутренняя ошибка сервера",
    "eng": "Internal server error"
  }
}
```
```json
{
  "application": "ebito-application-api",
  "endpoint": "/api/v1/...",
  "errorCode": 1001,
  "messages": {
    "rus": "Переданное сообщение невалидно",
    "eng": "Message not valid"
  },
  "payload": [
    {
      "field": "prop1",
      "message": "must be positive"
    },
    {
      "field": "prop2",
      "message": "must not be null or blank"
    }
  ]
}
```