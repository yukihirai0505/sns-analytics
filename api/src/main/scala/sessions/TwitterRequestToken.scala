package sessions

import com.softwaremill.session.{MultiValueSessionSerializer, SessionSerializer, SingleValueSessionSerializer}

import scala.util.Try

/**
  * Created by Yuky on 2018/05/16.
  */
case class TwitterRequestToken(token: String, tokenSecret: String)

object TwitterRequestToken {
  implicit def serializer: MyScalaMultiValueSessionSerializer =
    new MyScalaMultiValueSessionSerializer()
}

class MyScalaMultiValueSessionSerializer
  extends MultiValueSessionSerializer[TwitterRequestToken](
    (sco: TwitterRequestToken) => Map(
      "token" -> sco.token,
      "tokenSecret" -> sco.tokenSecret
    ),
    (m: Map[String, String]) => Try(
      new TwitterRequestToken(
        m("token"), m("tokenSecret")
      )
    )
  )
