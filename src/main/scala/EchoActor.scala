import akka.actor.{Actor, ActorSystem, Props}

class EchoActor extends Actor {

  var counter = 0

  override def receive = {
    case msg => counter += 1
      println(s"Counter:${counter}Hello Folks I am receiving a message '$msg'")
  }

}

object EchoActor extends App {
  val system = ActorSystem("EchoActor")
  val props = Props[EchoActor]
  val ref = system.actorOf(props)
  val ref1 = system.actorOf(props)  //separate thread for actor
  ref ! "1.Welcome to Knoldus!"
  ref1 ! "2.Welcome to Knoldus!"
  ref ! "3.Welcome to Knoldus!"
  ref ! "4.Welcome to Knoldus!"
  ref1 ! "5.Welcome to Knoldus!"
}
