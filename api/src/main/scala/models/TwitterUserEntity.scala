package models

case class TwitterUserEntity(userId: Long, twitterId: Long, twitterName: String, twitterScreenName: String, twitterAccessToken: String, id: Option[Long] = None)