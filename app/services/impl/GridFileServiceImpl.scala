package services.impl

import java.io.{File, FileInputStream}
import java.util.UUID
import javax.imageio.ImageIO

import com.mongodb.casbah.gridfs.GridFSDBFile
import com.sksamuel.scrimage.{Format, FormatDetector}
import domain.{FileMeta, FileResults}
import org.imgscalr.Scalr
import repository.FileRepository
import services.{FileTypeService, GridFileService}

import scala.concurrent.Future

/**
  * Created by hashcode on 2016/10/12.
  */
class GridFileServiceImpl extends GridFileService{
  def getFile(id: String): Option[GridFSDBFile] = {
    FileRepository.getFileById(id)
  }

  def processFile(data: File, meta: FileMeta): Future[Seq[FileResults]] = {
    import scala.concurrent.ExecutionContext.Implicits.global
    meta.contentType.startsWith("image") match {
      case true => Future {
        val ext = getFileExtension(data)
        val normal = resizeImage(data, ext, 650)
        val thumb = resizeImage(data, ext, 150)

        val thumbnailImageId = FileRepository.save(new FileInputStream(thumb), meta)
        val thumbnailImageMetaData = FileResults(
          thumbnailImageId.toString,
          "/api/static/" + thumbnailImageId.toString + "/" + getFileName(thumbnailImageId.toString),
          Some("Thumbnail"),
          meta.contentType)

        val normalImageId = FileRepository.save(new FileInputStream(normal), meta)
        val normalImageMetaData = FileResults(normalImageId.toString,
          "/api/static/" + normalImageId.toString + "/" + getFileName(normalImageId.toString),
          Some("Standard"),
          meta.contentType)

        val originId = FileRepository.save(new FileInputStream(data), meta)
        val originMetaData = FileResults(originId.toString,
          "/api/static/" + originId.toString + "/" + getFileName(originId.toString),
          Some("Original"),
          meta.contentType)

        Seq[FileResults](normalImageMetaData, thumbnailImageMetaData, originMetaData)
      }
      case false => Future {
        val fileId = FileRepository.save(new FileInputStream(data), meta)
        val fileMetaData = FileResults(fileId.toString,
          "/api/static/" + fileId.toString+"/"+getFileName(fileId.toString),
          None,
          meta.contentType)
        Seq[FileResults](fileMetaData)
      }
    }
  }

  private def checkMimeType(file: Any): String = {
    FileTypeService.detectFile(file)
  }

  private def getFileExtension(file: File): String = {
    val format = FormatDetector.detect(new FileInputStream(file))
    format match {
      case Some(Format.GIF) => "gif"
      case Some(Format.PNG) => "png"
      case Some(Format.JPEG) => "jpg"
      case _ => "jpeg"
    }
  }

  private def resizeImage(file: File, ext: String, size: Int): File = {
    val image = ImageIO.read(file)
    val thumbnail = Scalr.resize(image, size)
    val resizedImage = new File("/tmp/tmp" + UUID.randomUUID().toString +"."+ ext)
    ImageIO.write(thumbnail, ext, resizedImage)
    resizedImage
  }

  private def getFileName(id:String):String={
    FileRepository.getFileById(id) match {
      case Some(data) => data.filename.get
      case None => "nofile"
    }
  }
}

