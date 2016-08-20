package $package$

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.stream.ActorMaterializer
import de.choffmeister.microserviceutils.json._

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

class HttpServer(greeter: ActorRef)(implicit val system: ActorSystem) extends JsonProtocol {
  val routes = pathPrefix("api") {
    pathEnd {
      complete(HttpServer.jsonHome)
    } ~
    path("greet") {
      post {
        entity(as[Name]) { name =>
          askActor(greeter, name) {
            case greeting: Greeting => complete(greeting)
          }
        }
      }
    }
  }

  def askActor(actorRef: ActorRef, request: Any)(handle: PartialFunction[Any, Route]): Route = {
    onSuccess(actorRef.ask(request)(1.minute)) {
      case response if handle.isDefinedAt(response) =>
        handle(response)
      case response =>
        system.log.error("Received unexpected response '{}' after sending message '{}' to '{}'", response, request, actorRef)
        complete(StatusCodes.InternalServerError)
    }
  }

  def bind(): Future[ServerBinding] = {
    val httpConfig = system.settings.config.getConfig("http")
    val interface = httpConfig.getString("interface")
    val port = httpConfig.getInt("port")

    implicit val executor = system.dispatcher
    implicit val materializer = ActorMaterializer()

    Http().bindAndHandle(routes, interface, port).andThen {
      case Success(bind) => system.log.info(s"Started HTTP server on {}", bind.localAddress)
      case Failure(err) => system.log.error("Could not start HTTP server", err)
    }
  }
}

object HttpServer {
  val docsBaseUrl = "$docsBaseUrl$"

  val greetRel = s"\$docsBaseUrl/rel/greet"

  val jsonHome = JsonHome(Map(
    greetRel -> JsonHomeSingleResource("/greet")
  ))
}
