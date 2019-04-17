name := "tab-builder"

version := "0.1"

scalaVersion := "2.11.7"

lazy val versions = new {
  val finatra = "2.1.2"
  val logback = "1.1.3"
  val akka = "2.5.22"
  val mongo = "2.4.2"
}

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)

libraryDependencies += "com.twitter.finatra" % "finatra-http_2.11" % versions.finatra
libraryDependencies += "com.twitter.finatra" % "finatra-slf4j_2.11" % versions.finatra
libraryDependencies += "ch.qos.logback" % "logback-classic" % versions.logback
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % versions.akka
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % versions.mongo