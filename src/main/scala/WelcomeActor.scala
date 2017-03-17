import akka.actor.{Actor, Props}
import akka.actor._

import scala.concurrent.Future
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class WelcomeActor extends Actor {

  implicit val timeout = Timeout(1000 seconds)
  val messageActor = context.actorOf(Props[WelcomeChildActor])

  override def receive = {
    case x if x == Morning || x == Afternoon || x == Evening || x == Night =>
      val f: Future[String] = (messageActor ? x).mapTo[String]
      f pipeTo sender
  }

}


class WelcomeChildActor extends Actor {

  override def receive = {
    case Morning =>
      sender() ! "Good Morning!"
    case Afternoon =>
      sender() ! "Good Afternoon!"
    case Evening =>
      sender() ! "Good Evening!"
    case Night =>
      sender() ! "Good Night!"
  }

}

case object Morning
case object Afternoon
case object Evening
case object Night

object demoActorSystem extends App {
  implicit val timeout = Timeout(1000 seconds)
  val actorSystem = ActorSystem("PipeActorSystem")
  val props = Props[WelcomeActor]
  val actor = actorSystem.actorOf(props)
  val response: Future[String] = (actor ? Evening).mapTo[String]
  response.foreach(println)
}