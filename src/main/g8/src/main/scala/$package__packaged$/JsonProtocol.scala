package $package$

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import de.choffmeister.microserviceutils.json._
import spray.json._

trait JsonProtocol extends DefaultJsonProtocol
    with SprayJsonSupport
    with JsonHomeJsonProtocol
    with HalResourceJsonProtocol
    with UUIDJsonProtocol
    with InstantJsonProtocol {

  implicit val nameFormat = jsonFormat1(Name)
  implicit val greetingFormat = jsonFormat1(Greeting)
}

object JsonProtocol extends JsonProtocol
