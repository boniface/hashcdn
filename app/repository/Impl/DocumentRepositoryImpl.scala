package repository.Impl

import com.mongodb.BasicDBObject
import conf.connection.HashDB
import domain.Key
import org.mongodb.scala.result.{DeleteResult, UpdateResult}
import org.mongodb.scala.{Completed, Document, Observable}
import repository.DocumentRepository
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._

import scala.concurrent.Future

/**
  * Created by hashcode on 2016/10/11.
  */
class DocumentRepositoryImpl extends DocumentRepository{

  val database = HashDB.getDocumentConnection()


  val collection = database.getCollection("keys")

  override def createKey(key: Key): Future[Seq[Completed]] = {
    val doc = Document("_id" -> key.id, "value" -> key.value)
    collection.insertOne(doc).toFuture()
  }

  override def updateKey(key: Key): Future[Seq[UpdateResult]] = {
    collection.updateOne(equal("_id", key.id), set("value", key.value)).toFuture()
  }

  override def getKey(id: String):Future[Seq[Document]]  = {
    collection.find(equal("_id", id)).toFuture()
  }

  override def deleteKey(id: String): Future[Seq[DeleteResult]] = {
    collection.deleteOne(equal("_id", id)).toFuture()
  }
}
