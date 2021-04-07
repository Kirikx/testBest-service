INSERT INTO `user` (id, first_name, last_name, username, password, email)
VALUES ('92c1453eb7314d7b8c2c945339f9b1bf', 'John', 'Silver', 'admin', '$2y$12$w3LCJwUyEg10JD1rJarm.O1NZLr6jx15fqp1p0fmBGXRnwsnC9xo2', 'john.silver@mail.ru'),
       ('81f977c2ba6e478094a68212e647d4eb', 'Sarah', 'Connor', 'guest', '$2y$12$4gv/3QyfdAuwxjlwmC36bOc2kGJHoHVY9coRlnOah1QNezXUmq9WG', 'qwerty@gmail.com');

INSERT INTO `role` (id, name)
VALUES  ('950437035d14448d9487bc16c88cc5eb', 'ROLE_USER'),
        ('79b9dce1d9b6401cb19507e545574879', 'ROLE_MANAGER'),
        ('0c0040918eaa4fddbbe8afbdde7b6d71', 'ROLE_ADMIN');

INSERT INTO `user_role` (user_id, role_id)
SELECT (SELECT id FROM `user` WHERE username = 'admin'), (SELECT id FROM `role` WHERE name = 'ROLE_ADMIN')
UNION ALL
SELECT (SELECT id FROM `user` WHERE username = 'guest'), (SELECT id FROM `role` WHERE name = 'ROLE_USER');

INSERT INTO `question_type` (id, name)
VALUES  ('961dc8b96cc1444cb843c16f71ca9f04', 'Одиночный'),
        ('7f1d5c0cf6e4435683d8949e30b9b918', 'Множественный'),
        ('942c958498044ea486a60db30dcc681b', 'Вопрос со свободным ответом');