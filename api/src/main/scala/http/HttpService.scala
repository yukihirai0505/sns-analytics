package http

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import http.routes._
import services._
import utils.CorsSupport

import scala.concurrent.ExecutionContext

class HttpService(usersService: UsersService,
                  authService: AuthService
                  //rankingService: RankingService,
                 )(implicit executionContext: ExecutionContext, actorSystem: ActorSystem, materializer: ActorMaterializer) extends CorsSupport {

  val usersRouter = new UsersServiceRoute(authService, usersService)
  val authRouter = new AuthServiceRoute(authService)
  //val rankingRouter = new RankingServiceRoute(authService, rankingService)

  val routes =
    pathPrefix("v1") {
      corsHandler {
        usersRouter.route ~
          authRouter.route
      }
    }

}
