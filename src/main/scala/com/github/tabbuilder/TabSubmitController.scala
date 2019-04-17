package com.github.tabbuilder

import akka.actor.ActorRef
import com.twitter.finatra.http.Controller

class TabSubmitController extends Controller{

  val processingActor: ActorRef = TabProcessingActor.create()

  post("/data-submit/") { request: TabSubmissionRequest =>
    println(s"got data ${request.artist}-${request.title}: ${request.tab}")
    processingActor.tell(request, sender = ActorRef.noSender)
  }
}

case class TabSubmissionRequest(artist: String, title: String, tab: String)
