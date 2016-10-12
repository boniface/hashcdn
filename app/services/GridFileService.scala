package services

import java.io.File

import com.mongodb.casbah.gridfs.GridFSDBFile
import domain.{FileMeta, FileResults}
import services.impl.GridFileServiceImpl

import scala.concurrent.Future

/**
  * Created by hashcode on 2016/10/12.
  */
trait GridFileService {
  def getFile(id: String): Option[GridFSDBFile]

  def processFile(data: File, meta: FileMeta): Future[Seq[FileResults]]
}

object GridFileService {
  def apply: GridFileService = new GridFileServiceImpl()
}
