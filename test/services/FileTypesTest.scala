package services

import java.io.{FileOutputStream, FileInputStream, File}
import javax.imageio.ImageIO

import org.junit.{Ignore, Test}


/**
 * Created by hashcode on 2015/09/29.
 */
class FileTypesTest {
  @Test
  def testFileType(): Unit = {

    val url = getClass().getResource("image.jpg")
    val file = new File(url.getPath())
    val logo = new FileInputStream(file)
    //    val mimeType = FileTypeService.detectFile("http://localhost:9000/cdn/56095b6ebe24baa41da57f13")

    val outfile = FileServices.createImage(file)
//    val outfileThumb = FileServices.createThumb(file)
    val  image = ImageIO.read(outfile)
//    val  imageb = ImageIO.read(outfileThumb)
    ImageIO.write(image, "jpg",new File("/home/hashcode/hashprojects/hashcdn/test/services/hederle.jpg"))
//    ImageIO.write(imageb, "jpg",new File("/home/hashcode/hashprojects/hashcdn/test/services/thumb.jpg"))



  }

}
