CREATE TABLE IF NOT EXISTS `sns_analytics`.`users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ユーザー名',
  `profile_pic_url` varchar(500) NOT NULL COMMENT 'プロフィール写真用URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='ユーザー';
