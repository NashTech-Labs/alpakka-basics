package com.knoldus.utility

import akka.actor.ActorSystem
import akka.stream.Materializer

trait ActorJob {
  implicit val system: ActorSystem = ActorSystem("alpakka-demo")
  implicit val materializer: Materializer.type = Materializer
}
