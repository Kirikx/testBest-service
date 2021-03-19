CREATE TABLE `user` (
  `id` char(36) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
  `id` char(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_role` (
  `user_id` char(36) NOT NULL,
  `role_id` char(36) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_user_role_role_idx` (`role_id`),
  CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `topic` (
  `id` char(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(4096),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `question_type` (
  `id` char(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `question` (
  `id` char(36) NOT NULL,
  `topic_id` char(36) NOT NULL,
  `question_type_id` char(36) NOT NULL,
  `question` varchar(4096) NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_test_user_idx` (`question_type_id`),
  KEY `fk_test_topic_idx` (`topic_id`),
  CONSTRAINT `fk_question_topic` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`),
  CONSTRAINT `fk_question_question_type` FOREIGN KEY (`question_type_id`) REFERENCES `question_type` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `answer` (
  `id` char(36) NOT NULL,
  `question_id` char(36) NOT NULL,
  `answer` varchar(4096) NOT NULL,
  `correct` tinyint NOT NULL DEFAULT '0',
  `deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_answer_question_idx` (`question_id`) /*!80000 INVISIBLE */,
  CONSTRAINT `fk_answer_question_idx` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `test` (
  `id` char(36) NOT NULL,
  `topic_id` char(36) DEFAULT NULL,
  `author_id` char(36) DEFAULT NULL,
  `name` varchar(1024) NOT NULL,
  `description` varchar(4096),
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `duration` smallint NOT NULL,
  `deleted` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_test_user_idx` (`author_id`),
  KEY `fk_test_topic_idx` (`topic_id`),
  CONSTRAINT `fk_test_topic` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`),
  CONSTRAINT `fk_test_user` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `chapter` (
  `id` char(36) NOT NULL,
  `test_id` char(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(4096),
  `deleted` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_chapter_test_idx` (`test_id`),
  CONSTRAINT `fk_chapter_test` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `question_chapter` (
  `question_id` char(36) NOT NULL,
  `chapter_id` char(36) NOT NULL,
  PRIMARY KEY (`question_id`,`chapter_id`),
  KEY `fk_question_chapter_chapter_idx` (`chapter_id`),
  CONSTRAINT `fk_question_chapter_chapter` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_question_chapter_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_test` (
  `id` char(36) NOT NULL,
  `test_id` char(36) NOT NULL,
  `user_id` char(36) NOT NULL,
  `started` timestamp NULL DEFAULT NULL,
  `finished` timestamp NULL DEFAULT NULL,
  `score` smallint DEFAULT NULL,
  `passed` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_test_test_idx` (`test_id`),
  KEY `fk_user_test_user_idx` (`user_id`),
  CONSTRAINT `fk_user_test_test` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_user_test_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_test_question` (
  `id` char(36) NOT NULL,
  `user_test_id` char(36) NOT NULL,
  `question_id` char(36) NOT NULL,
  `correct` tinyint DEFAULT NULL,
  `user_answer` varchar(4096) DEFAULT NULL,
  `answered` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_test_question_user_test_idx` (`user_test_id`),
  KEY `user_test_question_question_idx` (`question_id`),
  CONSTRAINT `fk_user_test_question_user_test` FOREIGN KEY (`user_test_id`) REFERENCES `user_test` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_test_question_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `selected_answer` (
  `id` char(36) NOT NULL,
  `test_question_id` char(36) NOT NULL,
  `answer_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_selected_answer_user_test_question_idx` (`test_question_id`),
  CONSTRAINT `fk_selected_answer_user_test_question` FOREIGN KEY (`test_question_id`) REFERENCES `user_test_question` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;