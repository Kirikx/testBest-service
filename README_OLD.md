##TestOnline

Данное web-приложение представляет из себя сервис онлайн тестирования

####Руководство к сборке и запуску проекта

Сборка проекта осуществляется из рабочей директории \TestOnlineV2 по команде:

```bash
maven clean package
```
(clean можно опустить при первой сборке)

Запуск проекта осуществляется из директории TestOnlineV2\testonline-app\target по команде:

```bash
java -jar testonline-app.jar
```

В директории \TestOnlineV2\testonline-app\target\classes содержится конфигурационный файл database.properties содержащий настройки подключения к базе данный. Я использовал MySQL 8.0.

Для разворачивания базы в корневой директории содержится скрипт SQL testDB.sql

####Используемые технологии:

- Java 8

- Mysql 8.0.17

- Spring Boot 2.0.0

- Spring JDBC 5.2.3

- Spring Security 2.2.4

- Maven 3.3


####Описание сервисов доступа для роли  ADMIN

Для входа под ролью ADMIN необходимо в форме Login ввести логин «admin» и пароль «admin»

| **Метод запроса** | **url** | **Content-type** | **Описание** |
| --- | --- | --- | --- |
| GET | /, /login | home.html/ login.html (для не аутентифицированных пользователей) | Страница приветствия (и logout)/Форма аутентификации |
| GET/POST | /registration | registration.html | Форма регистрации новых участников |
| GET/POST | /admin/statistics | application/json | Общая статистика тестирования (п.8.1-8.4 задания) |
| GET | /admin/topics | application/json | Список тем тестирования |
| POST | /admin/topics | application/json | Создание новой темы |
| GET | /admin/topics/{id} | application/json | Получение записи темы по id |
| GET | /admin/topics/{topic\_id}/questions | application/json | Получение списка вопросов текущей темы |
| POST | /admin/topics/{topic\_id}/questions | application/json | Создание нового вопроса |
| GET | /admin/topics/{topic\_id}/questions/{question\_id} | application/json | Получение записи вопроса по question\_id |
| GET | /admin/topics/{topic\_id}/questions/{question\_id}/answers | application/json | Получение списка ответов текущего question\_id |
| POST | /admin/topics/{topic\_id}/questions/{question\_id}/answers | application/json | Создание нового ответа на вопрос |
| GET | /admin /users | application/json | Получение списка всех зарегистрированных пользователей |
| GET | /admin /users/{user\_id} | application/json | Получение записи пользователя по user\_id |
| GET | /admin /users/{user\_id}/answers | application/json | Получение списка ответов пользователя |
| GET | /admin/users/{user\_id}/statistics | application/json | Получения списка индивидуальной статистики пользователя |



####Описание сервисов доступа для роли  USER

| **Метод запроса** | **url** | **Content-type** | **Описание** |
| --- | --- | --- | --- |
| GET | / | home.html/ login.html (для не аутентифицированных пользователей) | Страница приветствия (и logout)/Форма аутентификации |
| GET | /registration | registration.html | Форма регистрации новых участников |
| GET | /statistics | application/json | Индивидуальная статистика пользователя (п. 9.1 задания* ) |
| GET | /answers | application/json | Получение списка ответов пользователя |
| GET | /topics | application/json | Список тем тестирования |
| GET | /topics/{id} | application/json | Получение записи темы по id |
| GET | /topics/{topic\_id}/questions | application/json | Получение списка вопросов текущей темы |
| GET | /topics/{topic\_id}/questions/{question\_id} | application/json | Получение записи вопроса по question\_id |
| GET | /topics/{topic\_id}/questions/{question\_id}?set={id\_answer} | application/json | Выбор ответа из предложенных вариантов |
| GET | /topics/{topic\_id}/questions/{question\_id}?input=user_answer | application/json | Ввод ответа |

\*Примечание – п.9.2 и п. 9.3 не выполнены в виду небольшой проблемы. Запросы для получения данной статистики содержатся в скрипте testDB.sql