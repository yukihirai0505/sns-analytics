package models

case class UserEntity(name: String, profilePicUrl: String, id: Option[Long] = None)
