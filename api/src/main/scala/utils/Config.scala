package utils

import com.typesafe.config.ConfigFactory
import twitter4j.conf.ConfigurationBuilder

trait Config {
  private val config = ConfigFactory.load()
  private val httpConfig = config.getConfig("http")
  private val databaseConfig = config.getConfig("database")
  private val twitterConfig = config.getConfig("twitter")

  val httpHost = httpConfig.getString("interface")
  val httpPort = httpConfig.getInt("port")

  val jdbcUrl = databaseConfig.getString("url")
  val dbUser = databaseConfig.getString("user")
  val dbPassword = databaseConfig.getString("password")

  val twitterConsumerKey = twitterConfig.getString("consumer-key")
  val twitterSecretKey = twitterConfig.getString("secret-key")
  val twitterConf = new ConfigurationBuilder()
    .setOAuthConsumerKey(twitterConsumerKey)
    .setOAuthConsumerSecret(twitterSecretKey)
    .build()

  val callbackUrl = config.getString("callback-url")
  val userCallbackUrl = config.getString("user-callback-url")

}
