package $package$

import akka.actor.{Actor, Props}

final case class Name(name: String)
final case class Greeting(greeting: String)

class Greeter extends Actor {
  override def receive = {
    case Name(name) => sender() ! Greeting(s"Hello, \$name!")
  }
}

object Greeter {
  def props = Props(classOf[Greeter])
}
