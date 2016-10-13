package controllers


import java.io.File
import java.nio.file.attribute.PosixFilePermission._
import java.nio.file.attribute.PosixFilePermissions
import java.nio.file.{Files, Path}
import java.util

import akka.stream.IOResult
import akka.stream.scaladsl._
import akka.util.ByteString
import domain.FileMeta
import play.api.libs.iteratee.Enumerator
import play.api.libs.json.Json
import play.api.libs.streams._
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc._
import play.core.parsers.Multipart.FileInfo
import services.{FileTypeService, GridFileService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


//  def upload = Action(parse.temporaryFile) { request =>
//    request.body.moveTo(new File("/tmp/picture/uploaded"))
//    Ok("File uploaded")
//  }
//curl -v -X POST http://localhost:9000/api/upload -F "upload=@/home/hashcode/0imagescript/images/image.jpg"
//02f62e18e9c91e0df732e08f60435841


/**
  * Created by hashcode on 2015/10/03.
  */
class GridFilesController extends Controller {
  private val logger = org.slf4j.LoggerFactory.getLogger(this.getClass)

  type FilePartHandler[A] = FileInfo => Accumulator[ByteString, FilePart[A]]

  def upload = Action.async(parse.multipartFormData) { request =>

    request.body.file("upload") match {
      case Some(file) => {
        val data = file.ref.file
        val meta = FileMeta(file.filename, FileTypeService.detectFile(data))
        val results = GridFileService.apply.processFile(data, meta)
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


    //    val input = request.body
    //    val entity = Json.fromJson[OrganisationType](input).get
    //    val response = for {
    //      auth <- TokenCheck.getToken(request)
    //      results <- OrganisationTypeService.apply.save(entity) if (auth.status == "VALID")
    //    } yield results
    //    response.map(ans => Ok(Json.toJson(ans.isExhausted)))
    //      .recover { case e: Exception => Unauthorized }


  }

  def getFile(id: String, filename: String) = Action {
    import scala.concurrent.ExecutionContext.Implicits.global
    GridFileService.apply.getFile(id) match {
      case Some(file) => {
        val dataContent: Enumerator[Array[Byte]] = Enumerator.fromStream(file.inputStream)
        val source = Source.fromPublisher(Streams.enumeratorToPublisher(dataContent))
        Ok.chunked(source).as(file.contentType.getOrElse(BINARY))
      }
      case None => NotFound
    }
  }



  private def handleFilePartAsFile: FilePartHandler[File] = {
    case FileInfo(partName, filename, contentType) =>
      val attr = PosixFilePermissions.asFileAttribute(util.EnumSet.of(OWNER_READ, OWNER_WRITE))
      val path: Path = Files.createTempFile("multipartBody", "tempFile", attr)
      val file = path.toFile
      val fileSink: Sink[ByteString, Future[IOResult]] = FileIO.toPath(path)
      val accumulator: Accumulator[ByteString, IOResult] = Accumulator(fileSink)
      accumulator.map {
        case IOResult(count, status) =>
          logger.info(s"count = $count, status = $status")
          FilePart(partName, filename, contentType, file)
      }(play.api.libs.concurrent.Execution.defaultContext)
  }

  def streamUpload = Action(parse.multipartFormData(handleFilePartAsFile)) { implicit request =>
    val fileOption = request.body.file("upload").map {
      case FilePart(key, filename, contentType, file) =>
        logger.info(s"key = ${key}, filename = ${filename}, contentType = ${contentType}, file = $file")
        val meta = FileMeta(file.getName, FileTypeService.detectFile(file))
        val results = GridFileService.apply.processFile(file, meta)
        results map (result => {
          Ok(Json.toJson(result))
        })
    }
    Ok(s"file size")

  }

}

