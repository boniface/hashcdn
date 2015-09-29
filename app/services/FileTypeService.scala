package services

import java.io.{InputStream, File}

import org.apache.tika.Tika

/**
 * Created by hashcode on 2015/09/29.
 */
object FileTypeService {
  val detect = new Tika()

  def detectByFileNameOrUrl(file:String): String ={
    detect.detect(file)
  }

  def detectByFile(file:File) ={

  }

  def detectByInputStream(file:InputStream)={

  }

  def detectByUrloRUrl(url:String): String ={
    detect.detect(url)
  }



}
