CREATE TABLE IF NOT EXISTS `sns_analytics`.`twitter_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT 'ユーザーID',
  `twitter_id` bigint(20) NOT NULL COMMENT 'TwitterID',
  `twitter_name` varchar(500) NOT NULL COMMENT 'Twitterユーザー名',
  `twitter_screen_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Twitter表示名',
  `twitter_access_token` varchar(500) NOT NULL COMMENT 'Twitterアクセストークン',
  PRIMARY KEY (`id`),
  KEY `fk_twitter_users1_idx` (`user_id`),
  CONSTRAINT `fk_twitter_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB COMMENT='Twitterユーザー';
