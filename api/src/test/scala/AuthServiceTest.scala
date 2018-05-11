import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server

class AuthServiceTest extends BaseServiceTest {

  trait Context {
    val testUsers = provisionUsersList(2)
    val route = httpService.authRouter.route
  }

  "Auth service" should {

    "register users and retrieve token" in new Context {
      val testUser = testUsers.head
      signInUser(route) {
        response.status should be(StatusCodes.MovedPermanently)
      }
    }
  }

  private def signInUser(route: server.Route)(action: => Unit) = {
    Get("/auth/signIn") ~> route ~> check(action)
  }

}
