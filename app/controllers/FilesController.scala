package controllers


import akka.stream.scaladsl.Source
import domain.FileMeta
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.Json
import play.api.libs.streams.Streams
import play.api.mvc._
import services.{FileServices, FileTypeService}

import scala.concurrent.Future


/**
  * Created by hashcode on 2015/10/03.
  */
class FilesController extends Controller {

  //  def upload = Action(parse.temporaryFile) { request =>
  //    request.body.moveTo(new File("/tmp/picture/uploaded"))
  //    Ok("File uploaded")
  //  }
  //curl -v -X POST http://localhost:9000/api/upload -F "upload=@/home/hashcode/0imagescript/images/image.jpg"


  def upload = Action.async(parse.multipartFormData) { request =>
    import scala.concurrent.ExecutionContext.Implicits.global
    request.body.file("upload") match {
      case Some(file) => {
        val data = file.ref.file
        val meta = FileMeta(file.filename, FileTypeService.detectFile(data))
        val results = FileServices.processFile(data, meta)
        results map (result => {
          Ok(Json.toJson(result))
        })
      }
      case None => {
        Future {
          BadRequest
        }
      }
    }
  }

  def getFile(id: String, filename: String) = Action {
    import scala.concurrent.ExecutionContext.Implicits.global
    FileServices.getFile(id) match {
      case Some(file) => {
        val dataContent: Enumerator[Array[Byte]] = Enumerator.fromStream(file.inputStream)
        val source = Source.fromPublisher(Streams.enumeratorToPublisher(dataContent))
        Ok.chunked(source).as(file.contentType.getOrElse(BINARY))
      }
      case None => NotFound
    }
  }
}

