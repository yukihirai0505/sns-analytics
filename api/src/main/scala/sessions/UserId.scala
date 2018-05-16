package sessions

import com.softwaremill.session.{SessionSerializer, SingleValueSessionSerializer}

import scala.util.Try

/**
  * Created by Yuky on 2018/05/16.
  */
case class UserId(userId: String)

object UserId {
  implicit def serializer: SessionSerializer[UserId, String] =
    new SingleValueSessionSerializer(_.userId,
      (un: String) =>
        Try {
          UserId(un)
        })
}