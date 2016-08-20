package $package$

import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import de.choffmeister.microserviceutils.json.JsonHome
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._

class HttpServerTest extends FlatSpec with ScalatestRouteTest with Matchers with JsonProtocol {
  import $package$.HttpServer._

  implicit val timeout = RouteTestTimeout(10.seconds)
  val greeter = system.actorOf(Greeter.props)
  val httpServer = new HttpServer(greeter)
  val routes = httpServer.routes

  "HttpServer" should "serve home resource" in {
    Get("/api") ~> routes ~> check {
      responseAs[JsonHome] should be(jsonHome)
    }
  }

  it should "greet" in {
    Post("/api" + jsonHome.resources(greetRel).expandHref(), Name("foobar")) ~> routes ~> check {
      responseAs[Greeting] should be(Greeting("Hello, foobar!"))
    }
  }
}
