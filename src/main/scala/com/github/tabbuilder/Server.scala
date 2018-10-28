package com.github.tabbuilder
import com.twitter.finagle.Filter
import com.twitter.finagle.http.filter.Cors
import com.twitter.finagle.http.filter.Cors.HttpFilter
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{Controller, HttpServer}
import com.twitter.finatra.logging.filter.{LoggingMDCFilter, TraceIdMDCFilter}

class Server extends HttpServer{

  override protected def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[CommonFilters]
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
    .filter(new HttpFilter(Cors.UnsafePermissivePolicy))
      .add[TabSubmitController]
  }
}


object ServerMain extends Server