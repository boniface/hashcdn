package services.impl

import domain.Key
import org.mongodb.scala.result._
import org.mongodb.scala.{Completed, Document}
import repository.DocumentRepository
import services.DocumentService

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hashcode on 2016/10/12.
  */
class DocumentServiceImpl extends DocumentService {

  override def createKey(key: Key): Future[Completed] = {
    DocumentRepository.apply.createKey(key) map (result => result.head)
  }

  override def updateKey(key: Key): Future[UpdateResult] = {
    DocumentRepository.apply.updateKey(key) map (result => result.head)
  }

  override def getKey(id: String): Future[Key] = {
    val results = DocumentRepository.apply.getKey(id)
    results map (documents => {
      val document = documents.head
      Key(id = _id(document), value(document))
    })
  }

  override def deleteKey(id: String): Future[DeleteResult] = {
    DocumentRepository.apply.deleteKey(id) map (result => result.head)
  }

  private def _id(document: Document) = document.get("_id") match {
    case Some(field) => field.asString().getValue
    case None => ""
  }

  private def value(document: Document) = document.get("value") match {
    case Some(field) => field.asString().getValue
    case None => ""
  }
}
