import java.util.regex.Pattern

val name = "wertimage/jpeg"


name.startsWith("image") match {
  case true => println(" This Has image")
  case false => println(" No Image Here")
}