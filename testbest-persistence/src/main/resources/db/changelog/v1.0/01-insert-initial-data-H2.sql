INSERT INTO `user` (id, first_name, last_name, username, password, email)
VALUES (UUID(), 'John', 'Silver', 'admin', '$2y$12$w3LCJwUyEg10JD1rJarm.O1NZLr6jx15fqp1p0fmBGXRnwsnC9xo2', 'john.silver@mail.ru'),
       (UUID(), 'Sarah', 'Connor', 'guest', '$2y$12$4gv/3QyfdAuwxjlwmC36bOc2kGJHoHVY9coRlnOah1QNezXUmq9WG', 'qwerty@gmail.com');

INSERT INTO `role` (id, name)
VALUES  (UUID(), 'ROLE_USER'),
        (UUID(), 'ROLE_MANAGER'),
        (UUID(), 'ROLE_ADMIN');

INSERT INTO `user_role` (user_id, role_id)
SELECT (SELECT id FROM `user` WHERE username = 'admin'), (SELECT id FROM `role` WHERE name = 'ROLE_ADMIN')
UNION ALL
SELECT (SELECT id FROM `user` WHERE username = 'guest'), (SELECT id FROM `role` WHERE name = 'ROLE_USER');

INSERT INTO `question_type` (id, name)
VALUES  (UUID(), 'Одиночный'),
        (UUID(), 'Множественный'),
        (UUID(), 'Вопрос со свободным ответом');