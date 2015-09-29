package conf.connection

import com.mongodb.casbah.{MongoClient, MongoClientURI, MongoDB}
import com.typesafe.config.ConfigFactory

/**
 * Created by hashcode on 2015/09/29.
 */
class Connection {

}

object Config{
  val config = ConfigFactory.load()

}

object HashDB {
  def getConnection(): MongoDB={
    val hosts = Config.config.getString("mongodb.host")
    val database = Config.config.getString("mongodb.database")
    val uri = MongoClientURI(hosts)
    MongoClient(uri)(database)
  }
}
