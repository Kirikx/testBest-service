CREATE TABLE `user` (
  `id` binary(16) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
);

CREATE TABLE `role` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_role` (
  `user_id` binary(16) NOT NULL,
  `role_id` binary(16) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  CONSTRAINT `fk_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
);

CREATE TABLE `topic` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(4096),
  PRIMARY KEY (`id`)
);

CREATE TABLE `question_type` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `question` (
  `id` binary(16) NOT NULL,
  `topic_id` binary(16) NOT NULL,
  `question_type_id` binary(16) NOT NULL,
  `question` varchar(4096) NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_question_topic` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`),
  CONSTRAINT `fk_question_question_type` FOREIGN KEY (`question_type_id`) REFERENCES `question_type` (`id`) ON DELETE CASCADE
);

CREATE TABLE `answer` (
  `id` binary(16) NOT NULL,
  `question_id` binary(16) NOT NULL,
  `answer` varchar(4096) NOT NULL,
  `correct` tinyint NOT NULL DEFAULT '0',
  `deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_answer_question_idx` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE
);

CREATE TABLE `test` (
  `id` binary(16) NOT NULL,
  `topic_id` binary(16) DEFAULT NULL,
  `author_id` binary(16) DEFAULT NULL,
  `name` varchar(1024) NOT NULL,
  `description` varchar(4096),
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `duration` smallint NOT NULL,
  `deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_test_topic` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`),
  CONSTRAINT `fk_test_user` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
);

CREATE TABLE `chapter` (
  `id` binary(16) NOT NULL,
  `test_id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(4096),
  `deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_chapter_test` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`) ON DELETE CASCADE
);

CREATE TABLE `question_chapter` (
  `question_id` binary(16) NOT NULL,
  `chapter_id` binary(16) NOT NULL,
  PRIMARY KEY (`question_id`,`chapter_id`),
  CONSTRAINT `fk_question_chapter_chapter` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_question_chapter_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE
);

CREATE TABLE `user_test` (
  `id` binary(16) NOT NULL,
  `test_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `started` timestamp NULL DEFAULT NULL,
  `finished` timestamp NULL DEFAULT NULL,
  `score` smallint DEFAULT NULL,
  `passed` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_test_test` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_user_test_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
);

CREATE TABLE `user_test_question` (
  `id` binary(16) NOT NULL,
  `user_test_id` binary(16) NOT NULL,
  `question_id` binary(16) NOT NULL,
  `correct` tinyint NOT NULL DEFAULT '0',
  `user_answer` varchar(4096) DEFAULT NULL,
  `answered` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_test_question_user_test` FOREIGN KEY (`user_test_id`) REFERENCES `user_test` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_test_question_question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE
);

CREATE TABLE `selected_answer` (
  `test_question_id` binary(16) NOT NULL,
  `answer_id` binary(16) NOT NULL,
  PRIMARY KEY (`test_question_id`, `answer_id`),
  CONSTRAINT `fk_selected_answer_user_test_question` FOREIGN KEY (`test_question_id`) REFERENCES `user_test_question` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_selected_answer_answer` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`) ON DELETE CASCADE
);