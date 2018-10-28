package com.github.tabbuilder

import com.twitter.finatra.http.Controller

class TabSubmitController extends Controller{

  post("/data-submit/") { request: TabSubmissionRequest =>
    println(s"got data ${request.artist}-${request.title}: ${request.tab}")
  }
}

case class TabSubmissionRequest(artist: String, title: String, tab: String)
