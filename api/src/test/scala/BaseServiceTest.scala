import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.heikoseeberger.akkahttpcirce.CirceSupport
import http.HttpService
import models._
import org.scalatest.{Matchers, WordSpec}
import services._
import utils.DatabaseService

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.Random

/**
  * author Yuki Hirai on 2017/06/27.
  */
trait BaseServiceTest extends WordSpec with Matchers with ScalatestRouteTest with CirceSupport {

  private val databaseService = new DatabaseService("jdbc:mysql://localhost:3306/sns_analytics?autoReconnect=true&useSSL=false&useUnicode=yes&characterEncoding=UTF-8&connectionCollation=utf8mb4_general_ci", "root", "root")

  val usersService = new UsersService(databaseService)
  val authService = new AuthService(databaseService)(usersService)
  //val rankingService = new RankingService(databaseService)
  val httpService = new HttpService(
    usersService, authService
  )

  def provisionUsersList(size: Int): Seq[UserEntity] = {
    val savedUsers = (1 to size).map { _ =>
      UserEntity(
        id = Some(Random.nextLong()),
        gaUserId = s"テスト${Random.nextInt()}",
        username = s"テスト${Random.nextInt()}",
        email = s"hogehoge${Random.nextInt()}"
      )
    }.map(usersService.createUser)

    Await.result(Future.sequence(savedUsers), 10.seconds)
  }

  def provisionTokensForUsers(usersList: Seq[UserEntity]) = {
    val savedTokens = usersList.map(authService.createToken)
    Await.result(Future.sequence(savedTokens), 10.seconds)
  }

}
