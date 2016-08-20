package $package$

import akka.actor.ActorSystem

object Main {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("$actorSystemName$")

    val greeter = system.actorOf(Greeter.props)
    val httpServer = new HttpServer(greeter)

    httpServer.bind()
  }
}
