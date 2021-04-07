INSERT INTO `topic` (id, name, description)
VALUES ('bf0e228249c04ccdb11c20aec0fd51f1', 'Викторина',
        'Пробный тест');

INSERT INTO `test` (id, topic_id, author_id, name, duration)
VALUES ('dec1571995f246a1931a865617972d70',
        'bf0e228249c04ccdb11c20aec0fd51f1',
        'e65d5367ef4d4f3d9d77bb6bd6e39ec7',
        'Пробный тест', 30);

INSERT INTO `chapter` (id, test_id, name)
VALUES ('baafe2c6aad54bbcbb5289ad9bf00d50',
        'dec1571995f246a1931a865617972d70',
        'Единственный раздел');

INSERT INTO `question` (id, question, topic_id, question_type_id)
VALUES ('7d96349025b543949c249797dc992302',
        'Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?',
        'bf0e228249c04ccdb11c20aec0fd51f1',
        '961dc8b96cc1444cb843c16f71ca9f04'),

       ('5ba638c249974109874343d4666b807e',
        'Какую пошлину ввели в XII  веке в Англии для того чтобы заставить мужчин пойти на войну?',
        'bf0e228249c04ccdb11c20aec0fd51f1',
        '961dc8b96cc1444cb843c16f71ca9f04'),

       ('db8c2ed72bc74be68bd5e5cc437bda9e',
        'Откуда пошло выражение деньги не пахнут?',
        'bf0e228249c04ccdb11c20aec0fd51f1',
        '961dc8b96cc1444cb843c16f71ca9f04'),

       ('d3755f1d639a42f790814ef36f939df7',
        'Туристы, приезжающие на Майорку, обязаны заплатить налог…',
        'bf0e228249c04ccdb11c20aec0fd51f1',
        '961dc8b96cc1444cb843c16f71ca9f04'),

       ('d9b9f41bd6f3477bbf5f43c3b8984c20',
        'Основой для «Сказки о рыбаке и рыбке» Пушкина послужила сказка братьев Гримм «Рыбак и его жена». В ней немецкая «коллега» нашей старухи превратилась в:',
        'bf0e228249c04ccdb11c20aec0fd51f1',
        '961dc8b96cc1444cb843c16f71ca9f04'),

       ('0cdae11503b3454680de950ebe2ddb74',
        'Российский мультфильм, удостоенный «Оскара», — это…',
        'bf0e228249c04ccdb11c20aec0fd51f1',
        '942c958498044ea486a60db30dcc681b'),

       ('3310eba3e157418386d602abe9d623b7',
        'В каком городе не работал великий композитор 18-го века Кристоф Виллибальд Глюк?',
        'bf0e228249c04ccdb11c20aec0fd51f1',
        '942c958498044ea486a60db30dcc681b');

INSERT INTO `question_chapter` (question_id, chapter_id)
VALUES ('7d96349025b543949c249797dc992302',
        'baafe2c6aad54bbcbb5289ad9bf00d50'),

       ('5ba638c249974109874343d4666b807e',
        'baafe2c6aad54bbcbb5289ad9bf00d50'),

       ('db8c2ed72bc74be68bd5e5cc437bda9e',
        'baafe2c6aad54bbcbb5289ad9bf00d50'),

       ('d3755f1d639a42f790814ef36f939df7',
        'baafe2c6aad54bbcbb5289ad9bf00d50'),

       ('d9b9f41bd6f3477bbf5f43c3b8984c20',
        'baafe2c6aad54bbcbb5289ad9bf00d50'),

       ('0cdae11503b3454680de950ebe2ddb74',
        'baafe2c6aad54bbcbb5289ad9bf00d50'),

       ('3310eba3e157418386d602abe9d623b7',
        'baafe2c6aad54bbcbb5289ad9bf00d50');

INSERT INTO `answer` (id, answer, correct, question_id)
VALUES ('efa27049f68645278b08739d35ee74dd', 'Джон Кеннеди', 0,
        '7d96349025b543949c249797dc992302'),

       ('86aa7f40b6754a09bf0d2cdd96c6f3ee', 'Франклин Рузвельт', 1,
        '7d96349025b543949c249797dc992302'),

       ('84361be4fb054d8a9c024e5fd12d0474', 'Рональд Рейган', 0,
        '7d96349025b543949c249797dc992302'),

       ('b98c67abd2f642b3b3153aa06f50c9ab', 'Налог на тунеядство', 0,
        '5ba638c249974109874343d4666b807e'),

       ('48fc46ef86514287a1474d18c0e4c13a', 'Налог на трусость', 1,
        '5ba638c249974109874343d4666b807e'),

       ('e7d3cabc31f14e368b1f699779cac722','Налог на отсутствие сапог', 0,
        '5ba638c249974109874343d4666b807e'),

       ('f4705d3e892b48cc9d4969706cff93bd','От подателей за провоз парфюмерии', 0,
        'db8c2ed72bc74be68bd5e5cc437bda9e'),

       ('65852c78856f4528a7c868d4aa6855be', 'От сборов за нестиранные носки', 0,
        'db8c2ed72bc74be68bd5e5cc437bda9e'),

       ('2bd119a3fc3b41a5ad3ea5e04290428f', 'От налога на туалеты', 1,
        'db8c2ed72bc74be68bd5e5cc437bda9e'),

       ('9214bd92ade14b049cc48787b2cfb762', 'На плавки', 0,
        'd3755f1d639a42f790814ef36f939df7'),

       ('02402164a2ba4bcb8a9acd6c49f51867', 'На пальмы', 0,
        'd3755f1d639a42f790814ef36f939df7'),

       ('b638e60802b8433e90963640c8778950', 'На солнце', 1,
        'd3755f1d639a42f790814ef36f939df7'),

       ('7b063f79f5634c76b48263dd52a00272', 'Папу Римского', 1,
        'd9b9f41bd6f3477bbf5f43c3b8984c20'),

       ('21b87c02918a41ff94d3e75bb162b656', 'Королеву', 0,
        'd9b9f41bd6f3477bbf5f43c3b8984c20'),

       ('2c873e3fa1af49ed959d9b90c5b80e07', 'Директора рыбзавода', 0,
        'd9b9f41bd6f3477bbf5f43c3b8984c20'),

       ('8cf416055c324cd8843c222553dbda99',
        'Командира отряда водолазов', 0,
        'd9b9f41bd6f3477bbf5f43c3b8984c20'),

       ('0fc7bdb38bf54fe99c1f83a6ac2b9b29', 'Старик и море', 1,
        '0cdae11503b3454680de950ebe2ddb74'),

       ('089aac9313fd45b585d07b227adecc68', 'Берлин', 1,
        '3310eba3e157418386d602abe9d623b7');