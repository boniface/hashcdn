package services

import java.io.{FileInputStream, File}

import com.mongodb.casbah.gridfs.GridFSDBFile
import domain.{FileMeta, FileResults}
import repository.FilesRepository

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/09/29.
 */
object FileServices {
  def getFile(id: String):Option[GridFSDBFile]= {
      FilesRepository.getFileById(id)
  }


  def processFile(data: File, meta: FileMeta):Future[Seq[FileResults]] = {
    import scala.concurrent.ExecutionContext.Implicits.global

    meta.contentType.startsWith("image") match {
      case true => Future {
        val r = FileResults("1","2",None)
        Seq[FileResults](r)
      }
      case false =>  Future{
        val res = FilesRepository.save(new FileInputStream(data),meta)
        val r = FileResults(res.toString,"/api//api/getfile/"+res.toString,None)
        Seq[FileResults](r)
      }
    }
  }


  def checkMimeType(file:Any):String={
    FileTypeService.detectFile(file)
  }

  def getFileMeta(file:Any):FileMeta={

    FileMeta("","")
  }

}
