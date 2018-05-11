package models.db

import models.TwitterUserEntity
import utils.DatabaseService

trait TwitterUserEntityTable {

  protected val databaseService: DatabaseService

  import databaseService.driver.api._
  import slick.jdbc.{GetResult => GR}

  implicit def GetResultTwitterUsersRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[Long]]): GR[TwitterUserEntity] = GR{
    prs => import prs._
      val r = (<<?[Long], <<[Long], <<[Long], <<[String], <<[String], <<[String])
      import r._
      TwitterUserEntity.tupled((_2, _3, _4, _5, _6, _1)) // putting AutoInc last
  }
  /** Table description of table twitter_users. Objects of this class serve as prototypes for rows in queries. */
  class TwitterUsers(_tableTag: Tag) extends Table[TwitterUserEntity](_tableTag, "twitter_users") {
    def * = (userId, twitterId, twitterName, twitterScreenName, twitterAccessToken, Rep.Some(id)) <> (TwitterUserEntity.tupled, TwitterUserEntity.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(userId), Rep.Some(twitterId), Rep.Some(twitterName), Rep.Some(twitterScreenName), Rep.Some(twitterAccessToken), Rep.Some(id)).shaped.<>({r=>import r._; _1.map(_=> TwitterUserEntity.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column user_id SqlType(BIGINT) */
    val userId: Rep[Long] = column[Long]("user_id")
    /** Database column twitter_id SqlType(BIGINT) */
    val twitterId: Rep[Long] = column[Long]("twitter_id")
    /** Database column twitter_name SqlType(VARCHAR), Length(500,true) */
    val twitterName: Rep[String] = column[String]("twitter_name", O.Length(500,varying=true))
    /** Database column twitter_screen_name SqlType(VARCHAR), Length(500,true) */
    val twitterScreenName: Rep[String] = column[String]("twitter_screen_name", O.Length(500,varying=true))
    /** Database column twitter_access_token SqlType(VARCHAR), Length(500,true) */
    val twitterAccessToken: Rep[String] = column[String]("twitter_access_token", O.Length(500,varying=true))
    /** Database column id SqlType(BIGINT), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table TwitterUsers */
  lazy val twitterUsers = new TableQuery(tag => new TwitterUsers(tag))
}
