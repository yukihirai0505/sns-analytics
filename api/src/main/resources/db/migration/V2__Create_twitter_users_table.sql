CREATE TABLE IF NOT EXISTS `sns_analytics`.`twitter_users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) NOT NULL COMMENT 'ユーザーID',
  `twitter_id` BIGINT(20) NOT NULL COMMENT 'TwitterID',
  `twitter_name` VARCHAR(500) NOT NULL COMMENT 'Twitterユーザー名',
  `twitter_screen_name` VARCHAR(500) NOT NULL COMMENT 'Twitter表示名',
  `twitter_access_token` VARCHAR(500) NOT NULL COMMENT 'Twitterアクセストークン',
  PRIMARY KEY (`id`),
    INDEX `fk_twitter_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_twitter_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `sns_analytics`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Twitterユーザー';