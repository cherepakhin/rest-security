## Создание REST-сервиса с идентификацией

[Источник](https://spring-projects.ru/guides/tutorials-bookmarks/)

Пример моделирует простой сервис закладок, который просто собирает URI и описание. Все закладки принадлежат учетной записи пользователя.

Создание пользователя:
```shell script
http -j PUT :8080/account username=anna password=pass role=ROLE_USER_3
```

Получение токена:
```shell script
http -f PUT :8080/login username="anna" password="pass"
```

На выходе получаем токе в headerе. Что-то типа такого:

```html
Authorization: BearereyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbm5hIiwiZXhwIjoxNTc4NDEzNDU2fQ.UaNayVfOIhnRXLVbb-D3brzbgDcuyKIuVwIVaBYp2I2f7XvgyzQA2_oTKLZprAWpyGDQMgVL9-WrUSd-NDZiGA
```

После этого можно общаться с сервисом , добавляя эту строку в header запросов:

```shell script
http  GET :8080/account/1 'Authorization: BearereyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbm5hIiwiZXhwIjoxNTc4NDEzNDU2fQ.UaNayVfOIhnRXLVbb-D3brzbgDcuyKIuVwIVaBYp2I2f7XvgyzQA2_oTKLZprAWpyGDQMgVL9-WrUSd-NDZiGA'
```

Будет получен ответ:
```html
{
    "arrayRole": [
        "ROLE_USER_3"
    ],
    "bookmarks": [],
    "id": 1,
    "password": "****",
    "role": "ROLE_USER_3",
    "username": "anna"
}

```

При обращении к http  GET :8080/account, будет возвращена ошибка **"Нет доступа"**.
Настройка для ролей сделана в [SecurityConfiguration.java](src/main/java/ru/perm/v/restsecurity/secutity/SecurityConfiguration.java)

```java
...
	.antMatchers(HttpMethod.GET, "/account")
	.hasRole("USER_2")

	// К /account/1 могут обращаться все
	.antMatchers("/account/1").permitAll()
...
```