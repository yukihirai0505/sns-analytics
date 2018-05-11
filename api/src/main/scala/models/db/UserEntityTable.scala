package models.db

import models.UserEntity
import utils.DatabaseService

trait UserEntityTable {

  protected val databaseService: DatabaseService

  import databaseService.driver.api._
  import slick.jdbc.{GetResult => GR}

  implicit def GetResultUsersRow(implicit e0: GR[String], e1: GR[Option[Long]]): GR[UserEntity] = GR {
    prs =>
      import prs._
      val r = (<<?[Long], <<[String], <<[String])
      import r._
      UserEntity.tupled((_2, _3, _1)) // putting AutoInc last
  }

  class Users(_tableTag: Tag) extends Table[UserEntity](_tableTag, "users") {
    def * = (name, profilePicUrl, Rep.Some(id)) <> (UserEntity.tupled, UserEntity.unapply)

    def ? = (Rep.Some(name), Rep.Some(profilePicUrl), Rep.Some(id)).shaped.<>({ r => import r._; _1.map(_ => UserEntity.tupled((_1.get, _2.get, _3))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    val name: Rep[String] = column[String]("name", O.Length(500, varying = true))
    val profilePicUrl: Rep[String] = column[String]("profile_pic_url", O.Length(500, varying = true))
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
  }

  lazy val users = new TableQuery(tag => new Users(tag))
}
