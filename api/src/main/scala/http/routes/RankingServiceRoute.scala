package http.routes

import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.CirceSupport
import http.SecurityDirectives
import io.circe.generic.auto._
import io.circe.syntax._
//import services.{AuthService, RankingService}

import scala.concurrent.ExecutionContext

/*
class RankingServiceRoute(val authService: AuthService,
                          rankingService: RankingService
                       )(implicit executionContext: ExecutionContext) extends CirceSupport with SecurityDirectives {

  import rankingService._

  val route = pathPrefix("ranking") {
    pathEndOrSingleSlash {
      get {
        complete(getRanking.map(_.sortBy(- _.score).asJson))
      }
    }
  }

}
*/