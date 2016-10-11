package repository

import java.io.FileInputStream

import com.mongodb.casbah.gridfs.GridFSDBFile
import domain.FileMeta
import org.bson.types.ObjectId
import repository.Impl.GridFileRepositioryImpl

/**
  * Created by hashcode on 2016/10/11.
  */
trait GridFileRepositiory {

  def getFileType(name: String): String

  def save(file: FileInputStream, meta: FileMeta): ObjectId

  def getFileById(id: String): Option[GridFSDBFile]

  def getFilesByName(fileName: String): Option[GridFSDBFile]

  def deleteFileById(id: String)

  def deleteFilesByName(fileName: String)
}

object GridFileRepositiory{
  def apply: GridFileRepositiory = new GridFileRepositioryImpl()
}
