INSERT INTO `user` (id, first_name, last_name, username, password, email)
VALUES (UNHEX(REPLACE('e65d5367-ef4d-4f3d-9d77-bb6bd6e39ec7', '-', '')), 'Pol', 'Alan', 'manager',
        '$2y$12$4gv/3QyfdAuwxjlwmC36bOc2kGJHoHVY9coRlnOah1QNezXUmq9WG', 'qwerty@gmail.com');

INSERT INTO `user_role` (user_id, role_id)
SELECT (SELECT id FROM `user` WHERE username = 'manager'),
       (SELECT id FROM `role` WHERE name = 'ROLE_MANAGER');

INSERT INTO `topic` (id, name, description)
VALUES (UNHEX(REPLACE('bf0e2282-49c0-4ccd-b11c-20aec0fd51f1', '-', '')), 'Викторина',
        'Пробный тест');

INSERT INTO `test` (id, topic_id, author_id, name, duration)
VALUES (UNHEX(REPLACE('dec15719-95f2-46a1-931a-865617972d70', '-', '')),
        UNHEX(REPLACE('bf0e2282-49c0-4ccd-b11c-20aec0fd51f1', '-', '')),
        UNHEX(REPLACE('e65d5367-ef4d-4f3d-9d77-bb6bd6e39ec7', '-', '')),
        'Пробный тест', 30);

INSERT INTO `chapter` (id, test_id, name)
VALUES (UNHEX(REPLACE('baafe2c6-aad5-4bbc-bb52-89ad9bf00d50', '-', '')),
        UNHEX(REPLACE('dec15719-95f2-46a1-931a-865617972d70', '-', '')),
        'Единственный раздел');

INSERT INTO `question` (id, question, topic_id, question_type_id)
VALUES (UNHEX(REPLACE('7d963490-25b5-4394-9c24-9797dc992302', '-', '')),
        'Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?',
        UNHEX(REPLACE('bf0e2282-49c0-4ccd-b11c-20aec0fd51f1', '-', '')),
        UNHEX(REPLACE('961dc8b9-6cc1-444c-b843-c16f71ca9f04', '-', ''))),

       (UNHEX(REPLACE('5ba638c2-4997-4109-8743-43d4666b807e', '-', '')),
        'Какую пошлину ввели в XII  веке в Англии для того чтобы заставить мужчин пойти на войну?',
        UNHEX(REPLACE('bf0e2282-49c0-4ccd-b11c-20aec0fd51f1', '-', '')),
        UNHEX(REPLACE('961dc8b9-6cc1-444c-b843-c16f71ca9f04', '-', ''))),

       (UNHEX(REPLACE('db8c2ed7-2bc7-4be6-8bd5-e5cc437bda9e', '-', '')),
        'Откуда пошло выражение «деньги не пахнут?',
        UNHEX(REPLACE('bf0e2282-49c0-4ccd-b11c-20aec0fd51f1', '-', '')),
        UNHEX(REPLACE('961dc8b9-6cc1-444c-b843-c16f71ca9f04', '-', ''))),

       (UNHEX(REPLACE('d3755f1d-639a-42f7-9081-4ef36f939df7', '-', '')),
        'Туристы, приезжающие на Майорку, обязаны заплатить налог…',
        UNHEX(REPLACE('bf0e2282-49c0-4ccd-b11c-20aec0fd51f1', '-', '')),
        UNHEX(REPLACE('961dc8b9-6cc1-444c-b843-c16f71ca9f04', '-', ''))),

       (UNHEX(REPLACE('d9b9f41b-d6f3-477b-bf5f-43c3b8984c20', '-', '')),
        'Основой для «Сказки о рыбаке и рыбке Пушкина послужила сказка братьев Гримм «Рыбак и его жена». В ней немецкая «коллега» нашей старухи превратилась в:',
        UNHEX(REPLACE('bf0e2282-49c0-4ccd-b11c-20aec0fd51f1', '-', '')),
        UNHEX(REPLACE('961dc8b9-6cc1-444c-b843-c16f71ca9f04', '-', ''))),

       (UNHEX(REPLACE('0cdae115-03b3-4546-80de-950ebe2ddb74', '-', '')),
        'Российский мультфильм, удостоенный «Оскара», — это…',
        UNHEX(REPLACE('bf0e2282-49c0-4ccd-b11c-20aec0fd51f1', '-', '')),
        UNHEX(REPLACE('942c9584-9804-4ea4-86a6-0db30dcc681b', '-', ''))),

       (UNHEX(REPLACE('3310eba3-e157-4183-86d6-02abe9d623b7', '-', '')),
        'В каком городе не работал великий композитор 18-го века Кристоф Виллибальд Глюк?',
        UNHEX(REPLACE('bf0e2282-49c0-4ccd-b11c-20aec0fd51f1', '-', '')),
        UNHEX(REPLACE('942c9584-9804-4ea4-86a6-0db30dcc681b', '-', '')));

INSERT INTO `question_chapter` (question_id, chapter_id)
VALUES (UNHEX(REPLACE('7d963490-25b5-4394-9c24-9797dc992302', '-', '')),
        UNHEX(REPLACE('baafe2c6-aad5-4bbc-bb52-89ad9bf00d50', '-', ''))),

       (UNHEX(REPLACE('5ba638c2-4997-4109-8743-43d4666b807e', '-', '')),
        UNHEX(REPLACE('baafe2c6-aad5-4bbc-bb52-89ad9bf00d50', '-', ''))),

       (UNHEX(REPLACE('db8c2ed7-2bc7-4be6-8bd5-e5cc437bda9e', '-', '')),
        UNHEX(REPLACE('baafe2c6-aad5-4bbc-bb52-89ad9bf00d50', '-', ''))),

       (UNHEX(REPLACE('d3755f1d-639a-42f7-9081-4ef36f939df7', '-', '')),
        UNHEX(REPLACE('baafe2c6-aad5-4bbc-bb52-89ad9bf00d50', '-', ''))),

       (UNHEX(REPLACE('d9b9f41b-d6f3-477b-bf5f-43c3b8984c20', '-', '')),
        UNHEX(REPLACE('baafe2c6-aad5-4bbc-bb52-89ad9bf00d50', '-', ''))),

       (UNHEX(REPLACE('0cdae115-03b3-4546-80de-950ebe2ddb74', '-', '')),
        UNHEX(REPLACE('baafe2c6-aad5-4bbc-bb52-89ad9bf00d50', '-', ''))),

       (UNHEX(REPLACE('3310eba3-e157-4183-86d6-02abe9d623b7', '-', '')),
        UNHEX(REPLACE('baafe2c6-aad5-4bbc-bb52-89ad9bf00d50', '-', '')));

INSERT INTO `answer` (id, answer, correct, question_id)
VALUES (UNHEX(REPLACE('efa27049-f686-4527-8b08-739d35ee74dd', '-', '')), 'Джон Кеннеди', 0,
        UNHEX(REPLACE('7d963490-25b5-4394-9c24-9797dc992302', '-', ''))),

       (UNHEX(REPLACE('86aa7f40-b675-4a09-bf0d-2cdd96c6f3ee', '-', '')), 'Франклин Рузвельт', 1,
        UNHEX(REPLACE('7d963490-25b5-4394-9c24-9797dc992302', '-', ''))),

       (UNHEX(REPLACE('84361be4-fb05-4d8a-9c02-4e5fd12d0474', '-', '')), 'Рональд Рейган', 0,
        UNHEX(REPLACE('7d963490-25b5-4394-9c24-9797dc992302', '-', ''))),

       (UNHEX(REPLACE('b98c67ab-d2f6-42b3-b315-3aa06f50c9ab', '-', '')), 'Налог на тунеядство', 0,
        UNHEX(REPLACE('5ba638c2-4997-4109-8743-43d4666b807e', '-', ''))),

       (UNHEX(REPLACE('48fc46ef-8651-4287-a147-4d18c0e4c13a', '-', '')), 'Налог на трусость', 1,
        UNHEX(REPLACE('5ba638c2-4997-4109-8743-43d4666b807e', '-', ''))),

       (UNHEX(REPLACE('e7d3cabc-31f1-4e36-8b1f-699779cac722', '-', '')),
        'Налог на отсутствие сапог', 0,
        UNHEX(REPLACE('5ba638c2-4997-4109-8743-43d4666b807e', '-', ''))),

       (UNHEX(REPLACE('f4705d3e-892b-48cc-9d49-69706cff93bd', '-', '')),
        'От подателей за провоз парфюмерии', 0,
        UNHEX(REPLACE('db8c2ed7-2bc7-4be6-8bd5-e5cc437bda9e', '-', ''))),

       (UNHEX(REPLACE('65852c78-856f-4528-a7c8-68d4aa6855be', '-', '')),
        'От сборов за нестиранные носки', 0,
        UNHEX(REPLACE('db8c2ed7-2bc7-4be6-8bd5-e5cc437bda9e', '-', ''))),

       (UNHEX(REPLACE('2bd119a3-fc3b-41a5-ad3e-a5e04290428f', '-', '')), 'От налога на туалеты', 1,
        UNHEX(REPLACE('db8c2ed7-2bc7-4be6-8bd5-e5cc437bda9e', '-', ''))),

       (UNHEX(REPLACE('9214bd92-ade1-4b04-9cc4-8787b2cfb762', '-', '')), 'На плавки', 0,
        UNHEX(REPLACE('d3755f1d-639a-42f7-9081-4ef36f939df7', '-', ''))),

       (UNHEX(REPLACE('02402164-a2ba-4bcb-8a9a-cd6c49f51867', '-', '')), 'На пальмы', 0,
        UNHEX(REPLACE('d3755f1d-639a-42f7-9081-4ef36f939df7', '-', ''))),

       (UNHEX(REPLACE('b638e608-02b8-433e-9096-3640c8778950', '-', '')), 'На солнце', 1,
        UNHEX(REPLACE('d3755f1d-639a-42f7-9081-4ef36f939df7', '-', ''))),

       (UNHEX(REPLACE('7b063f79-f563-4c76-b482-63dd52a00272', '-', '')), 'Папу Римского', 1,
        UNHEX(REPLACE('d9b9f41b-d6f3-477b-bf5f-43c3b8984c20', '-', ''))),

       (UNHEX(REPLACE('21b87c02-918a-41ff-94d3-e75bb162b656', '-', '')), 'Королеву', 0,
        UNHEX(REPLACE('d9b9f41b-d6f3-477b-bf5f-43c3b8984c20', '-', ''))),

       (UNHEX(REPLACE('2c873e3f-a1af-49ed-959d-9b90c5b80e07', '-', '')), 'Директора рыбзавода', 0,
        UNHEX(REPLACE('d9b9f41b-d6f3-477b-bf5f-43c3b8984c20', '-', ''))),

       (UNHEX(REPLACE('8cf41605-5c32-4cd8-843c-222553dbda99', '-', '')),
        'Командира отряда водолазов', 0,
        UNHEX(REPLACE('d9b9f41b-d6f3-477b-bf5f-43c3b8984c20', '-', ''))),

       (UNHEX(REPLACE('0fc7bdb3-8bf5-4fe9-9c1f-83a6ac2b9b29', '-', '')), 'Старик и море', 1,
        UNHEX(REPLACE('0cdae115-03b3-4546-80de-950ebe2ddb74', '-', ''))),

       (UNHEX(REPLACE('089aac93-13fd-45b5-85d0-7b227adecc68', '-', '')), 'Берлин', 1,
        UNHEX(REPLACE('3310eba3-e157-4183-86d6-02abe9d623b7', '-', '')));