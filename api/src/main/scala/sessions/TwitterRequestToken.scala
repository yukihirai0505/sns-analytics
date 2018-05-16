package sessions

import com.softwaremill.session.{SessionSerializer, SingleValueSessionSerializer}

import scala.util.Try

/**
  * Created by Yuky on 2018/05/16.
  */
case class TwitterRequestToken(tokenSecret: String)

object TwitterRequestToken {
  implicit def serializer: SessionSerializer[TwitterRequestToken, String] =
    new SingleValueSessionSerializer(_.tokenSecret,
      (un: String) =>
        Try {
          TwitterRequestToken(un)
        })
}