package $package$

import akka.actor.ActorSystem
import de.choffmeister.microserviceutils.ServiceBase

import scala.concurrent.ExecutionContext

class Service(implicit val system: ActorSystem, val executor: ExecutionContext) extends ServiceBase {
  val greeter = system.actorOf(Greeter.props)
  val httpServer = new HttpServer(greeter)
}
