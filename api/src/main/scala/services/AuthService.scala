package services

import models.db.{TokenEntityTable, TwitterUserEntityTable}
import models.{TokenEntity, UserEntity}
import twitter4j.{Twitter, User}
import utils.DatabaseService

import scala.concurrent.{ExecutionContext, Future}

class AuthService(val databaseService: DatabaseService)
                 (usersService: UsersService)(implicit executionContext: ExecutionContext)
  extends TokenEntityTable with TwitterUserEntityTable {

  import databaseService._
  import databaseService.driver.api._

  def signIn(twitterId: Long): Future[Option[TokenEntity]] = {
    db.run((for {
      twitterUser <- twitterUsers.filter(_.twitterId === twitterId)
      user <- users.filter(_.id === twitterUser.userId)
    } yield user).result.headOption).flatMap {
      case Some(user) => db.run(tokens.filter(_.userId === user.id).result.headOption).flatMap {
        case Some(token) => Future.successful(Some(token))
        case None => createToken(user).map(token => Some(token))
      }
      case None => Future.successful(None)
    }
  }

  def signUp(user: User, accessToken: String): Future[TokenEntity] = {
    usersService.createUser(user, accessToken).flatMap(user => createToken(user))
  }

  def authenticate(token: String): Future[Option[UserEntity]] =
    db.run((for {
      token <- tokens.filter(_.token === token)
      user <- users.filter(_.id === token.userId)
    } yield user).result.headOption)

  def createToken(user: UserEntity): Future[TokenEntity] = db.run {
    tokens returning tokens.map(_.id) into ((token, id) => token.copy(id = Some(id))) += TokenEntity(userId = user.id.get)
  }

}
