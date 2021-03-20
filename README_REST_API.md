####Описание сервисов доступа для роли  ADMIN

Для входа под ролью ADMIN необходимо в форме Login ввести логин «test» и пароль «test»

| **Метод запроса** | **url** | **Content-type** | **Описание** |
| --- | --- | --- | --- |
| GET | /users/all | application/json | Получение всех пользователей |
| GET | /users/{id} | application/json | Получение конкретного пользователя по Id |


Описание сервисов доступа для роли  USER

| **Метод запроса** | **url** | **Content-type** | **Описание** |
| --- | --- | --- | --- |
| GET | /answers | application/json | Получение списка ответов пользователя |
| PUT | /answers/{id}/edit | application/json | Изменение ответа |
| POST | /answers/create | application/json | Создание ответа |
| GET | /answers/{id} | application/json | Получение одного ответа |

Описание сервисов Question

| **Метод запроса** | **url** | **Content-type** | **Описание** |
| --- | --- | --- | --- |
| GET | /questions/{id} | application/json | Получение вопроса по id |
| GET | /questions | application/json | Получение списка вопросов темы |
| DELETE | /questions/{id} | application/json | Удаление вопроса темы по id |
| POST | /questions/create | application/json | Создание вопроса темы |
| PUT | /questions/{id}/edit | application/json | Редактирование вопроса темы по id |


Описание сервисов Topic

| **Метод запроса** | **url** | **Content-type** | **Описание** |
| --- | --- | --- | --- |
| GET | /topics | application/json | Список тем тестирования |
| POST | /topics/create | application/json | Создание новой темы |
| GET| /topics/{id} | application/json | Найти тему по id |
| PUT | /topic/{id}/edit | application/json | Изменение записи темы по id |
| DELETE | /topic/{id} | application/json | Удаление темы по id |

Описание сервисов Авторизации

| **Метод запроса** | **url** | **Content-type** | **Описание** |
| --- | --- | --- | --- |
| POST | /register | application/json | Регистрация |
| POST | /signin | application/json | Вход |
