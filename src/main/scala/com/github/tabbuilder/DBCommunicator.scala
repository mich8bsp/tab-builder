package com.github.tabbuilder

import java.util.concurrent.Executors

import org.bson.types.ObjectId
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.{Completed, MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry

import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source

case class DBCommunicator(connection: MongoDatabase)(implicit ec: ExecutionContext){
  import DBCommunicator.TABS_COLLECTION

  def store(request: TabSubmissionRequest): Future[Completed] = {
    val collection: MongoCollection[DBTabs] = connection.getCollection(TABS_COLLECTION)
    collection.insertOne(DBTabs(request)).toFuture()
  }
}

object DBCommunicator{
  val TABS_COLLECTION = "tabs"
  val TABS_DB_NAME = "tabs-db-cluster"

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(3))
  def build: DBCommunicator = DBCommunicator(createConnection(TABS_DB_NAME))

  private def getCredentials: (String, String) = {
    val config = Source.fromFile("db.config").getLines()
      .next()
      .split(":")
    (config(0), config(1))
  }

  private def createConnection(dbName: String): MongoDatabase = {
    val creds: (String, String) = getCredentials
    val uri: String = s"mongodb+srv://${creds._1}:${creds._2}@tabs-db-cluster-ilxsz.mongodb.net/$dbName?retryWrites=true"
    System.setProperty("org.mongodb.async.type", "netty")
    val mongoClient: MongoClient = MongoClient(uri)
    mongoClient.getDatabase(dbName)
  }
}

object DBTabs{
  val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[DBTabs]), DEFAULT_CODEC_REGISTRY )

  def apply(request: TabSubmissionRequest): DBTabs = new DBTabs(
    ObjectId.get(),
    artist = request.artist,
    title = request.title,
    tabContent =  request.tab
  )
}

case class DBTabs(_id: ObjectId, artist: String, title: String, tabContent: String)