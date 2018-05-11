CREATE TABLE IF NOT EXISTS `sns_analytics`.`tokens` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(20) NOT NULL,
  `token` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tokens_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_tokens_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `sns_analytics`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'アクセストークン';