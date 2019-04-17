package com.github.tabbuilder

import java.util.concurrent.Executors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.concurrent.ExecutionContext

class TabProcessingActor extends Actor{
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(2))
  lazy val dbCommunicator: DBCommunicator = DBCommunicator.build

  override def receive: Receive = {
    case x@TabSubmissionRequest(_,_,_) => dbCommunicator.store(x)
      .map(_ => println("Stored tabs successfully"))
      .recover({
        case e: Throwable => println(s"Failed to store tabs with error ${e.getMessage}")
      })
  }
}

object TabProcessingActor{
  val actorSystem = ActorSystem("tab-processing-system")

  def create(): ActorRef = actorSystem.actorOf(Props(new TabProcessingActor))
}
