INSERT INTO `user` (id, first_name, last_name, username, password, email)
VALUES ('e65d5367ef4d4f3d9d77bb6bd6e39ec7', 'Pol', 'Alan', 'manager',
        '$2y$12$4gv/3QyfdAuwxjlwmC36bOc2kGJHoHVY9coRlnOah1QNezXUmq9WG', 'qwerty@gmail.com');

INSERT INTO `user_role` (user_id, role_id)
SELECT (SELECT id FROM `user` WHERE username = 'manager'),
       (SELECT id FROM `role` WHERE name = 'ROLE_MANAGER');