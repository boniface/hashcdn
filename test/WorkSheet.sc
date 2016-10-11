import java.util.regex.Pattern

import org.mongodb.scala.ServerAddress
import scala.collection.JavaConverters._

val name = "wertimage/jpeg"


name.startsWith("image") match {
  case true => println(" This Has image")
  case false => println(" No Image Here")
}
val hosts ="10.32.0.25,10.32.0.21,10.32.0.21"
val hlist = List(hosts) map (host =>new ServerAddress(host) )

val res = hlist.asJava

val list = List(ServerAddress("1.2.2.3"),ServerAddress("1.2.2.3"))
