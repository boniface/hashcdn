package conf.connection


import com.mongodb.casbah.{MongoClient, MongoClientURI, MongoDB}
import com.typesafe.config.ConfigFactory
import org.mongodb.scala.connection.ClusterSettings
import org.mongodb.scala.{MongoClientSettings, MongoDatabase, ServerAddress, MongoClient => ScalaMongoClient}

import scala.collection.JavaConverters._
/**
 * Created by hashcode on 2015/09/29.
 */

object Config{
  val config = ConfigFactory.load()

}

object HashDB {

  def getGridFsConnection(): MongoDB={
    val hosts = Config.config.getString("mongodb.cabHost")
    val database = Config.config.getString("mongodb.gridFsDB")
    val uri = MongoClientURI(hosts)
    MongoClient(uri)(database)
  }


  def getDocumentConnection():MongoDatabase = {
    val hosts = Config.config.getString("mongodb.host")
    val database = Config.config.getString("mongodb.docsDB")
    val clusterHosts = List(hosts) map (host => new ServerAddress(host))
    val clusterSettings: ClusterSettings = ClusterSettings.builder().hosts(clusterHosts.asJava).build()
    val settings: MongoClientSettings = MongoClientSettings.builder().clusterSettings(clusterSettings).build()
    val mongoClient: ScalaMongoClient = ScalaMongoClient(settings)
    mongoClient.getDatabase(database)
  }
}
