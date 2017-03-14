package $package$

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import com.typesafe.config.{Config, ConfigFactory}
import de.choffmeister.microserviceutils.http.JsonWebTokenAuthenticator
import de.choffmeister.microserviceutils.json.JsonHome
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{FunSpec, Matchers}

import scala.concurrent.duration._

abstract class ServiceTestBase extends FunSpec with ScalatestRouteTest with Matchers with ScalaFutures with JsonProtocol {
  // configure timeouts
  implicit val timeout: RouteTestTimeout = RouteTestTimeout(3.seconds)
  implicit val defaultPatience: PatienceConfig = PatienceConfig(timeout = Span(5, Seconds), interval = Span(100, Millis))

  // common needed values
  val config: Config = ConfigFactory.load()
  val authenticator: JsonWebTokenAuthenticator = new JsonWebTokenAuthenticator()
  val service: Service = new Service()
  val jsonHome: JsonHome = HttpServer.jsonHome
  def routes: Route = service.httpServer.routes
}
