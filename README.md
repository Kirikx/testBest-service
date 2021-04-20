# testBest-service
Service online testing

##Функциональные требования:

1. Должна быть возможность "регистрации".
2. Должна быть возможность залогиниться под зарегистрированным пользователем, чтобы была возможность работы в системе нескольких пользователей.
3. Должна быть возможность добавить вопросы и ответы на них. Вопросы комбинируются в темы называемые "тематики".
3.1 Контент (список вопросов, название, время выполнения) может редакировать менеджер создавший его.
4. Вопросы могут быть 3х типов: со свободным вводом ответа, с выбором одного ответа из нескольких вариантов, выбор нескольких вариантов.
5. Должна быть возможность добавить от имени текущего пользователя ответ на вопрос с сохранением ответа в бд.
5.1 На одной странице 1 вопрос, отвечаем по очереди на все вопросы (визард)
6. Система должна проверить введенный пользователем ответ на вопрос и сверить его с правильным ответов на этот вопрос. Результат проверки сохраняется в бд для конкретного пользователя.
7. Должна быть возможность пользователю посмотреть только свои не правильные ответы.
7.1 Статистика по разделам и в общем на странице подготовки к тесту. (На данный момент не реализовано)
8. Должна быть возможность посмотреть статистику прохождения "тестирования". В статистике должно отображаться: (На данный момент не реализовано)
    8.1.   сколько всего пользователей зарегистрировано в системе.
    8.2.   сколько пользователей прошли тестирование.
    8.3.   сколько пользователей ответили на все вопросы тестирования.
    8.4.   сколько пользователей ответили на все вопросы тестирования правильно. 
9. Должна быть возможность посмотреть статистику по текущему пользователю: (На данный момент не реализовано)
    9.1.   процент прохождения тестирования текущего пользователя (сколько правильных ответов он дал).
    9.2.   сколько процентов людей справилось с тестированием хуже текущего пользователя.
    9.3.   сколько процентов людей справилось с тестированием лучше текущего пользователя.
10 Критерии прохождения теста для конечной оценки (при создании теста)
   

####Бэклог:
Если успеем, дополнительные требования:
1. Этап "тестирование пользователя":
- добавить таймер тестирования. При начале теста таймер запускается и ответы в БД можно сохранять до его окончания (при создании вопросов в топиках указывать время на выполнение);
- фиксировать время потраченное на ответ по каждому вопросу (добавить соответствующую статистику);
- текст вопроса должен быть защищен от копирования (не реализовано);
2. Этап "после окончания тестирования" Выводится список неправильных ответов с возможностью добавления коментария к каждому из вопросов. (не реализовано)
- добавить коментарии к вопросам (для добавления ссылок на материаллы или отзыва о вопросе);
- Коментарий к топику целиком;
- доступ к тесту по ссылке (возможен редирект на страничку регистрации с последующим переходом к тесту);

Если хватит сил (не реализовано):
- нотификация на почту о результате тестирования;
- клиент для телеграмма;

###Роли в приложении:
- Админ - права на модерацию + удаление топиков, вопросов + распределение прав;
- Менеджер - создание/редактирование своих тестов + просмотр статистики по тестам и по пользователям (не реализовано);
- Пользователь - прохождение тестов + просмотр индивидуальной статистики

###Технологии:
1. Бэк:
- java 8;
- springboot;
- spring-data (удобно для скорости разработки, но поплатимся временем отклика);
- hibernate;
- maven;
- liquibase;
- swagger;
2. Фронт:
- angular;

###Распределение ролей разработчиков:
- Фронт - аналитик - дизайнер - Алексей;
- Бэк - аналитик - тестировшик - Николай;
- Бэк - аналитик - тестировшик - Максим;
- Тимлид - РM - PO - бек - Кирилл

##Политика контроля версий:
- master - ветка только для релизных билдов, туда сливаем протестированные исходники из ветки develop
- develop - основная ветка разработки, от нее отпачковываемся для реализации фич
- feature/%NAME_FEATURE% - ветки реализии задачь, создаем при реализации каждой новой задачи
После завершения работы над фичей, делаем пул-реквест в ветку develop (если есть конфликты - подмерживаем к себе 
в ветку изменения из develop, решаем конфликт, и сново делаем пул).

В конце спринта (за 3 дня до окончания) договариваемся о код-фризе - что значит что дальше лучше ничего не добавлять в 
ветку develop. За эти 3 дня нужно собрать и задеплоить проект. Далее мержим в ветку master (таким образом у нас будут рабочие проекты 
с нарастающим функционалом)