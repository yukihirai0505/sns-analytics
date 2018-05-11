import io.circe.generic.auto._
import models.RankingEntity
import org.scalatest.concurrent.ScalaFutures

/*
class RankingServiceTest extends BaseServiceTest with ScalaFutures {

  trait Context {
    val testUsers = provisionUsersList(5)
    val testTokens = provisionTokensForUsers(testUsers)
    val route = httpService.rankingRouter.route
  }
  "Users service" should {

    "retrieve ranking list" in new Context {
      Get("/ranking") ~> route ~> check {
        responseAs[Seq[RankingEntity]].isEmpty should be(false)
      }
    }
  }

}
*/