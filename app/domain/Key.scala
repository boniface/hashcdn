package domain

import play.api.libs.json.Json

/**
  * Created by hashcode on 2016/10/11.
  */
case class Key(id:String,value:String)

object Key {
  implicit val keyFmt = Json.format[Key]
}
