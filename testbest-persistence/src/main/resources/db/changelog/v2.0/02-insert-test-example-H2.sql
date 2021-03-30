INSERT INTO `topic` (id, name, description)
VALUES (UUID(), 'Викторина',
        'Пробный тест');

INSERT INTO `test` (id, topic_id, author_id, name, duration)
VALUES (UUID(),
        (SELECT id FROM `topic` WHERE name = 'Викторина'),
        (SELECT id FROM `user` WHERE username = 'manager'),
        'Пробный тест', 30);

INSERT INTO `chapter` (id, test_id, name)
VALUES (UUID(),
        (SELECT id FROM `test` WHERE name = 'Пробный тест'),
        'Единственный раздел');

INSERT INTO `question` (id, question, topic_id, question_type_id)
VALUES (UUID(),
        'Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?',
        (SELECT id FROM `topic` WHERE name = 'Викторина'),
        (SELECT id FROM `question_type` WHERE name = 'Одиночный')),

       (UUID(),
        'Какую пошлину ввели в XII  веке в Англии для того чтобы заставить мужчин пойти на войну?',
        (SELECT id FROM `topic` WHERE name = 'Викторина'),
        (SELECT id FROM `question_type` WHERE name = 'Одиночный')),

       (UUID(),
        'Откуда пошло выражение деньги не пахнут?',
        (SELECT id FROM `topic` WHERE name = 'Викторина'),
        (SELECT id FROM `question_type` WHERE name = 'Одиночный')),

       (UUID(),
        'Туристы, приезжающие на Майорку, обязаны заплатить налог…',
        (SELECT id FROM `topic` WHERE name = 'Викторина'),
        (SELECT id FROM `question_type` WHERE name = 'Одиночный')),

       (UUID(),
        'Основой для «Сказки о рыбаке и рыбке» Пушкина послужила сказка братьев Гримм «Рыбак и его жена». В ней немецкая «коллега» нашей старухи превратилась в:',
        (SELECT id FROM `topic` WHERE name = 'Викторина'),
        (SELECT id FROM `question_type` WHERE name = 'Одиночный')),

       (UUID(),
        'Российский мультфильм, удостоенный «Оскара», — это…',
        (SELECT id FROM `topic` WHERE name = 'Викторина'),
        (SELECT id FROM `question_type` WHERE name = 'Вопрос со свободным ответом')),

       (UUID(),
        'В каком городе не работал великий композитор 18-го века Кристоф Виллибальд Глюк?',
        (SELECT id FROM `topic` WHERE name = 'Викторина'),
        (SELECT id FROM `question_type` WHERE name = 'Вопрос со свободным ответом'));

INSERT INTO `question_chapter` (question_id, chapter_id)
VALUES ((SELECT id FROM `question` WHERE question = 'Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?'),
        (SELECT id FROM `chapter` WHERE name = 'Единственный раздел')),

       ((SELECT id FROM `question` WHERE question = 'Какую пошлину ввели в XII  веке в Англии для того чтобы заставить мужчин пойти на войну?'),
        (SELECT id FROM `chapter` WHERE name = 'Единственный раздел')),

       ((SELECT id FROM `question` WHERE question = 'Откуда пошло выражение деньги не пахнут?'),
        (SELECT id FROM `chapter` WHERE name = 'Единственный раздел')),

       ((SELECT id FROM `question` WHERE question = 'Туристы, приезжающие на Майорку, обязаны заплатить налог…'),
        (SELECT id FROM `chapter` WHERE name = 'Единственный раздел')),

       ((SELECT id FROM `question` WHERE question = 'Основой для «Сказки о рыбаке и рыбке» Пушкина послужила сказка братьев Гримм «Рыбак и его жена». В ней немецкая «коллега» нашей старухи превратилась в:'),
        (SELECT id FROM `chapter` WHERE name = 'Единственный раздел')),

       ((SELECT id FROM `question` WHERE question = 'Российский мультфильм, удостоенный «Оскара», — это…'),
        (SELECT id FROM `chapter` WHERE name = 'Единственный раздел')),

       ((SELECT id FROM `question` WHERE question = 'В каком городе не работал великий композитор 18-го века Кристоф Виллибальд Глюк?'),
        (SELECT id FROM `chapter` WHERE name = 'Единственный раздел'));

INSERT INTO `answer` (id, answer, correct, question_id)
VALUES (UUID(), 'Джон Кеннеди', 0,
        (SELECT id FROM `question` WHERE question = 'Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?')),

       (UUID(), 'Франклин Рузвельт', 1,
        (SELECT id FROM `question` WHERE question = 'Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?')),

       (UUID(), 'Рональд Рейган', 0,
        (SELECT id FROM `question` WHERE question = 'Кто из президентов США написал свой собственный рассказ про Шерлока Холмса?')),

       (UUID(), 'Налог на тунеядство', 0,
        (SELECT id FROM `question` WHERE question = 'Какую пошлину ввели в XII  веке в Англии для того чтобы заставить мужчин пойти на войну?')),

       (UUID(), 'Налог на трусость', 1,
        (SELECT id FROM `question` WHERE question = 'Какую пошлину ввели в XII  веке в Англии для того чтобы заставить мужчин пойти на войну?')),

       (UUID(),'Налог на отсутствие сапог', 0,
        (SELECT id FROM `question` WHERE question = 'Какую пошлину ввели в XII  веке в Англии для того чтобы заставить мужчин пойти на войну?')),

       (UUID(),'От подателей за провоз парфюмерии', 0,
        (SELECT id FROM `question` WHERE question = 'Откуда пошло выражение деньги не пахнут?')),

       (UUID(), 'От сборов за нестиранные носки', 0,
        (SELECT id FROM `question` WHERE question = 'Откуда пошло выражение деньги не пахнут?')),

       (UUID(), 'От налога на туалеты', 1,
        (SELECT id FROM `question` WHERE question = 'Откуда пошло выражение деньги не пахнут?')),

       (UUID(), 'На плавки', 0,
        (SELECT id FROM `question` WHERE question = 'Туристы, приезжающие на Майорку, обязаны заплатить налог…')),

       (UUID(), 'На пальмы', 0,
        (SELECT id FROM `question` WHERE question = 'Туристы, приезжающие на Майорку, обязаны заплатить налог…')),

       (UUID(), 'На солнце', 1,
        (SELECT id FROM `question` WHERE question = 'Туристы, приезжающие на Майорку, обязаны заплатить налог…')),

       (UUID(), 'Папу Римского', 1,
        (SELECT id FROM `question` WHERE question = 'Основой для «Сказки о рыбаке и рыбке» Пушкина послужила сказка братьев Гримм «Рыбак и его жена». В ней немецкая «коллега» нашей старухи превратилась в:')),

       (UUID(), 'Королеву', 0,
        (SELECT id FROM `question` WHERE question = 'Основой для «Сказки о рыбаке и рыбке» Пушкина послужила сказка братьев Гримм «Рыбак и его жена». В ней немецкая «коллега» нашей старухи превратилась в:')),

       (UUID(), 'Директора рыбзавода', 0,
        (SELECT id FROM `question` WHERE question = 'Основой для «Сказки о рыбаке и рыбке» Пушкина послужила сказка братьев Гримм «Рыбак и его жена». В ней немецкая «коллега» нашей старухи превратилась в:')),

       (UUID(),
        'Командира отряда водолазов', 0,
        (SELECT id FROM `question` WHERE question = 'Основой для «Сказки о рыбаке и рыбке» Пушкина послужила сказка братьев Гримм «Рыбак и его жена». В ней немецкая «коллега» нашей старухи превратилась в:')),

       (UUID(), 'Старик и море', 1,
        (SELECT id FROM `question` WHERE question = 'Российский мультфильм, удостоенный «Оскара», — это…')),

       (UUID(), 'Берлин', 1,
        (SELECT id FROM `question` WHERE question = 'В каком городе не работал великий композитор 18-го века Кристоф Виллибальд Глюк?'));