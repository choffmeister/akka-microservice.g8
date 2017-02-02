package $package$

import akka.actor.ActorSystem

class Service(implicit val system: ActorSystem) {
  val greeter = system.actorOf(Greeter.props)
  val httpServer = new HttpServer(greeter)
}
