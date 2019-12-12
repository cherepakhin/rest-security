## Создание REST-сервиса с идентификацией

[Источник](https://spring-projects.ru/guides/tutorials-bookmarks/)

Пример моделирует простой сервис закладок, который просто собирает URI и описание. Все закладки принадлежат учетной записи пользователя.

Все URI начинаются с имени пользователя. Таким образом, для аккаунта bob мы можем обратиться как */users/bob* или просто */bob*. Для доступа к списку закладок пользователя мы можем опуститься на уровень ниже(подобно файловой системе) к ресурсу */bob/bookmarks*.
