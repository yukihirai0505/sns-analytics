CREATE TABLE IF NOT EXISTS `sns_analytics`.`users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(500) NOT NULL COMMENT 'ユーザー名',
  `profile_pic_url` VARCHAR(500) NOT NULL COMMENT 'プロフィール写真用URL',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = 'ユーザー';