package http.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.softwaremill.session.SessionDirectives._
import com.softwaremill.session.SessionOptions._
import com.softwaremill.session._
import de.heikoseeberger.akkahttpcirce.CirceSupport
import http.SecurityDirectives
import services.AuthService
import sessions.{TwitterRequestToken, UserId}
import twitter4j.TwitterFactory
import twitter4j.auth.{AccessToken, RequestToken}
import utils.Config

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Failure

/**
  * ref: http://takezoe.hatenablog.com/entry/2018/01/30/141750
  * ref: https://github.com/softwaremill/akka-http-session/blob/master/example/src/main/scala/com/softwaremill/example/ScalaExample.scala
  *
  * @param authService
  * @param executionContext
  * @param actorSystem
  * @param materializer
  */
class AuthServiceRoute(val authService: AuthService)
                      (implicit executionContext: ExecutionContext,
                       actorSystem: ActorSystem,
                       materializer: ActorMaterializer
                      )
  extends CirceSupport
    with SecurityDirectives with Config {

  import StatusCodes._
  import authService._

  val sessionConfig = SessionConfig.default(SessionUtil.randomServerSecret())
  val userIdSessionManager = new SessionManager[UserId](sessionConfig)
  val twitterRequestTokenSessionManager = new SessionManager[TwitterRequestToken](sessionConfig)

  def setTwitterRequestTokenSession(v: TwitterRequestToken) = setSession(oneOff(twitterRequestTokenSessionManager), usingCookies, v)

  def requiredTwitterRequestTokenSession = requiredSession(oneOff(twitterRequestTokenSessionManager), usingCookies)

  def setUserIdSession(v: UserId) = setSession(oneOff(userIdSessionManager), usingCookies, v)

  def requiredUserIdSession = requiredSession(oneOff(userIdSessionManager), usingCookies)

  val route = pathPrefix("auth") {
    path("signIn") {
      pathEndOrSingleSlash {
        get {
          val twitter = new TwitterFactory(twitterConf).getInstance()
          val requestToken = twitter.getOAuthRequestToken(callbackUrl)
          setTwitterRequestTokenSession(TwitterRequestToken(requestToken.getToken, requestToken.getTokenSecret)) {
            redirect(requestToken.getAuthenticationURL, SeeOther)
          }
        }
      }
    } ~
      path("callback") {
        pathEndOrSingleSlash {
          get {
            requiredTwitterRequestTokenSession { session =>
              parameters('oauth_token.as[String], 'oauth_verifier.as[String]) { (oauthToken, oauthVerifier) =>
                val twitter = new TwitterFactory(twitterConf).getInstance()
                val accessToken: AccessToken = twitter.getOAuthAccessToken(
                  new RequestToken(session.token, session.tokenSecret),
                  oauthVerifier
                )
                twitter.verifyCredentials()
                val token: Future[String] = signIn(twitter.getId).flatMap {
                  case Some(t) => Future successful t.token
                  case None =>
                    val user = twitter.showUser(twitter.getId)
                    signUp(user, accessToken.getToken).flatMap(t => Future successful t.token)
                }
                onComplete(token) {
                  case scala.util.Success(value) =>
                    setUserIdSession(UserId(value)) {
                      complete(StatusCodes.OK)
                    }
                  case Failure(ex) => complete((InternalServerError, s"An error occurred: ${ex.getMessage}"))
                }
              }
            }
          }
        }
      }
  }

}
