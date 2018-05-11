package http.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, StatusCodes, Uri}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpcirce.CirceSupport
import http.SecurityDirectives
import io.circe.generic.auto._
import models.UserEntity
import org.apache.commons.codec.binary.Base64._
import services.AuthService
import twitter4j.TwitterFactory
import twitter4j.auth.{AccessToken, RequestToken}
import twitter4j.conf.{Configuration, ConfigurationBuilder}
import utils.Config
import com.softwaremill.session.SessionDirectives._
import com.softwaremill.session.SessionOptions._
import com.softwaremill.session.SessionResult._
import com.softwaremill.session._
import scala.util.Try


import scala.concurrent.{ExecutionContext, Future}
import scala.util.Failure

class AuthServiceRoute(val authService: AuthService)
                      (implicit executionContext: ExecutionContext,
                       actorSystem: ActorSystem,
                       materializer: ActorMaterializer
                      )
  extends CirceSupport
    with SecurityDirectives with Config {

  import StatusCodes._
  import authService._

  case class MyScalaSession(username: String)

  object MyScalaSession {
    implicit def serializer: SessionSerializer[MyScalaSession, String] =
      new SingleValueSessionSerializer(_.username,
        (un: String) =>
          Try {
            MyScalaSession(un)
          })
  }

  val sessionConfig = SessionConfig.default(SessionUtil.randomServerSecret())
  implicit val sessionManager = new SessionManager[MyScalaSession](sessionConfig)

  val route = pathPrefix("auth") {
    path("signIn") {
      pathEndOrSingleSlash {
        get {
          val twitter = new TwitterFactory(twitterConf).getInstance()
          val requestToken = twitter.getOAuthRequestToken(callbackUrl)
          setSession(oneOff, usingCookies, MyScalaSession("id")) {
            redirect(requestToken.getAuthenticationURL, SeeOther)
          }
        }
      }
    } ~
      path("callback") {
        pathEndOrSingleSlash {
          get {
            parameters('oauth_token.as[String], 'oauth_verifier.as[String]) { (oauthToken, oauthVerifier) =>
              val twitter = new TwitterFactory(twitterConf).getInstance()
              requiredSession(oneOff, usingCookies) { session =>
                val accessToken: AccessToken = twitter.getOAuthAccessToken("", oauthVerifier)
                twitter.verifyCredentials()
                val token: Future[String] = signIn(twitter.getId).flatMap {
                  case Some(t) => Future successful t.token
                  case None =>
                    val user = twitter.showUser(twitter.getId)
                    signUp(user, accessToken.getToken).flatMap(t => Future successful t.token)
                }
                onComplete(token) {
                  case scala.util.Success(value) => redirect(s"$userCallbackUrl?token=$value", Found)
                  case Failure(ex) => complete((InternalServerError, s"An error occurred: ${ex.getMessage}"))
                }
              }
            }
          }
        }
      }
  }

}
