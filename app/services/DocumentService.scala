package services

import domain.Key
import org.mongodb.scala.Completed
import org.mongodb.scala.result._
import services.impl.DocumentServiceImpl

import scala.concurrent.Future

/**
  * Created by hashcode on 2016/10/12.
  */
trait  DocumentService {
  def createKey(key: Key): Future[Completed]

  def updateKey(key: Key): Future[UpdateResult]

  def getKey(id: String):  Future[Key]

  def deleteKey(id: String): Future[DeleteResult]

}
object DocumentService{
  def apply: DocumentService = new DocumentServiceImpl()
}



