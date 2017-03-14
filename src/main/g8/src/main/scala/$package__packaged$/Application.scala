package $package$

import akka.Done
import akka.actor.ActorSystem
import de.choffmeister.microserviceutils.GracefulShutdownExtension
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Application {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("$actorSystemName$")
    val log = LoggerFactory.getLogger(getClass)
    GracefulShutdownExtension(system)

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
  }
}
