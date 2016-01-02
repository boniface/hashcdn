package domain

import play.api.libs.json.Json

/**
 * Created by hashcode on 2015/10/04.
 */
case class FileResults(
                        id: String,
                        url: String,
                        size: Option[String],
                        mime:String
                        )


object FileResults {

  implicit val advertFmt = Json.format[FileResults]

}
