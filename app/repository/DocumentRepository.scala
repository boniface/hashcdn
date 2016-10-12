package repository

import domain.Key
import org.mongodb.scala._
import org.mongodb.scala.result._
import repository.Impl.DocumentRepositoryImpl

import scala.concurrent.Future

/**
  * Created by hashcode on 2016/10/11.
  */
trait DocumentRepository {
  def createKey(key: Key): Future[Seq[Completed]]

  def updateKey(key: Key): Future[Seq[UpdateResult]]

  def getKey(id: String):  Future[Seq[Document]]

  def deleteKey(id: String): Future[Seq[DeleteResult]]

}
object DocumentRepository{
  def apply: DocumentRepository = new DocumentRepositoryImpl()
}
