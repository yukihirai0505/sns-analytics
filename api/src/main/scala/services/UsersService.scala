package services

import models.db.{TwitterUserEntityTable, UserEntityTable}
import models.{TwitterUserEntity, UserEntity}
import twitter4j.User
import utils.DatabaseService

import scala.concurrent.{ExecutionContext, Future}

class UsersService(val databaseService: DatabaseService)
                  (implicit executionContext: ExecutionContext)
  extends UserEntityTable with TwitterUserEntityTable {

  import databaseService._
  import databaseService.driver.api._

  def getUsers: Future[Seq[UserEntity]] = db.run(users.result)

  def getUserById(id: Long): Future[Option[UserEntity]] = db.run(users.filter(_.id === id).result.headOption)

  def createUser(user: User, accessToken: String): Future[UserEntity] = {
    val action = for {
      newUser <- users returning users.map(_.id) into ((user, id) => user.copy(id = Some(id))) += UserEntity(
        name = user.getName,
        profilePicUrl = user.getProfileImageURLHttps
      )
      _ <- twitterUsers += TwitterUserEntity(
        userId = newUser.id.get,
        twitterId = user.getId,
        twitterName = user.getName,
        twitterScreenName = user.getScreenName,
        twitterAccessToken = accessToken
      )
    } yield newUser
    db.run(action)
  }

  def updateUser(id: Long, userUpdate: UserEntity): Future[Option[UserEntity]] = getUserById(id).flatMap {
    case Some(user) =>
      val updatedUser = user
      db.run(users.filter(_.id === id).update(updatedUser)).map(_ => Some(updatedUser))
    case None => Future.successful(None)
  }

  def deleteUser(id: Long): Future[Int] = db.run(users.filter(_.id === id).delete)

}