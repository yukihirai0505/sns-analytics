package http

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.{BasicDirectives, FutureDirectives, RouteDirectives}
import models.UserEntity
import services.AuthService

trait SecurityDirectives {

  import BasicDirectives._
  import FutureDirectives._
  import RouteDirectives._

  def authenticate: Directive1[UserEntity] = {
    headerValueByName("Token").flatMap { token =>
      onSuccess(authService.authenticate(token)).flatMap {
        case Some(user) => provide(user)
        case None => reject
      }
    }
  }

  protected val authService: AuthService

}
