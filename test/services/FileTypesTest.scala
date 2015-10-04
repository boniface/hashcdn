package services

import java.io.{FileInputStream, File}

import org.junit.Test


/**
 * Created by hashcode on 2015/09/29.
 */
class FileTypesTest {
  @Test
  def testFileType(): Unit ={
    val  url = getClass().getResource("image.jpg")
    val  file = new File(url.getPath())
    val logo = new FileInputStream(file)

    val mimeType = FileTypeService.detectFile("http://localhost:9000/cdn/56095b6ebe24baa41da57f13")

    println("The Results is ",mimeType)

  }

}
