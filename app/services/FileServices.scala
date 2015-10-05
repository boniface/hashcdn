package services

import java.io.{ByteArrayInputStream, FileInputStream, File}

import com.mongodb.casbah.gridfs.GridFSDBFile
import com.sksamuel.scrimage.ScaleMethod.{Bicubic, FastScale}
import com.sksamuel.scrimage.nio.{PngWriter, GifWriter, JpegWriter}
import com.sksamuel.scrimage.{Position, FormatDetector, Format, Image}
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

  def createThumb(file: File):File={
    val format = FormatDetector.detect(new FileInputStream(file))

    format match {
      case Some(Format.GIF) => {Image.fromFile(file).fit(180,120).output(file)((GifWriter()))}
      case Some(Format.JPEG)=> Image.fromFile(file).fit(180,120).output(file)((JpegWriter()))
      case Some(Format.PNG)=> Image.fromFile(file).fit(180,120).output(file)((PngWriter()))
      case _ =>Image.fromFile(file).fit(180,120).output(file)
    }
  }

  def createImage(file: File):File={
    val format = FormatDetector.detect(new FileInputStream(file))
    val out = file
    format match {
      case Some(Format.GIF) => { println(" GIF CALLED ")
        Image.fromFile(file).scaleTo(650,500).output(file)((GifWriter()))}
      case Some(Format.JPEG)=> { println(" JPEG CALLED ")
        Image.fromFile(out).scale(0.5, Bicubic).output(out)}
      case Some(Format.PNG)=> { println(" PNG CALLED ")
        Image.fromFile(file).scaleTo(650,500).output(file)((PngWriter()))}
      case _ => { println(" DEFAULT CALLED ")
        Image.fromFile(file).scaleTo(650,500).output(file)
      }
    }
  }
}
