package $package$

import akka.Done
import akka.actor.ActorSystem
import de.choffmeister.microserviceutils.ShutdownDelay

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Application {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("$actorSystemName$")

    val service = new Service()
    val init = for {
      _ <- service.httpServer.bind()
    } yield Done

    init.onComplete {
      case Success(_) =>
        system.log.info("Initialization done")
      case Failure(cause) =>
        system.log.error(cause, "Initialization error")
        Thread.sleep(1000L)
        System.exit(1)
    }

    ShutdownDelay.registerShutdownHook()
    ShutdownDelay.isShuttingDown.onSuccess { case delay =>
      system.log.info("Received signal to terminate, shutting down in {} ms", delay.toMillis)
    }
  }
}
