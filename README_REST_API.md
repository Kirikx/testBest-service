####Описание сервисов доступа для роли  ADMIN

Для входа под ролью ADMIN необходимо в форме Login ввести логин «test» и пароль «test»

| **Метод запроса** | **url** | **Content-type** | **Описание** |
| --- | --- | --- | --- |
| GET | /topics | application/json | Список тем тестирования |
| POST | /topic/create | application/json | Создание новой темы |
| GET| /topic | application/json | Найти тему по id |
| PUT | /topic/{id}/edit | application/json | Изменение записи темы по id |
| DELETE | /topic/{id} | application/json | Удаление темы по id |
| GET | /topic/{id}/questions | application/json | Получение всех вопросов темы |
| GET | /topic/{id}/question/{id} | application/json | Получение одного вопроса темы по id |
| PUT | /topic/{id}/question/{id}/edit | application/json | Редактирование вопроса темы по id |
| DELETE | /topic/question/{id} | application/json | Удаление вопроса темы по id |
| POST | /topic/{id}/question/create | application/json | Создание вопроса темы |
| GET | /allusers | application/json | Получение всех пользователей |
| GET | /users/{id} | application/json | Получение конкретного пользователя по Id |


Описание сервисов доступа для роли  USER

| **Метод запроса** | **url** | **Content-type** | **Описание** |
| --- | --- | --- | --- |
| GET | /answers | application/json | Получение списка ответов пользователя |
| GET | /topics | application/json | Список тем тестирования |
| GET | /topics/{id} | application/json | Получение записи темы по id |
| GET | /topic/{id}/question/{id} | application/json | Получение вопроса по id |
| GET | /topic/{id}/questions | application/json | Получение списка вопросов темы |
| GET | /topic/{id}/question/{id}/answer/{id} | application/json | Получение ответа на вопрос |
| POST | /topic/{id}/question/{id}/answer/{id}/edit | application/json | Изменение ответа |

Описание сервисов Авторизации

| **Метод запроса** | **url** | **Content-type** | **Описание** |
| --- | --- | --- | --- |
| POST | /register | application/json | Регистрация |
| POST | /signin | application/json | Вход |
