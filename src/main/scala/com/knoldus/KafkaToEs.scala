package com.knoldus

import akka.{Done, NotUsed}
import akka.stream.alpakka.elasticsearch.WriteMessage
import akka.stream.alpakka.elasticsearch.scaladsl.ElasticsearchSink
import akka.stream.scaladsl.{Flow, Sink}
import com.knoldus.Sources.kafkaPlainSource
import com.knoldus.model.Person
import com.knoldus.utility.ActorJob
import org.apache.http.HttpHost
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.elasticsearch.client.RestClient
import play.api.libs.json.{Json, OFormat}
import spray.json.{JsObject, JsString, JsonWriter}

import scala.concurrent.Future

object KafkaToEs extends App with ActorJob {
implicit val format: OFormat[Person] = Json.format[Person]
  implicit val client: RestClient = RestClient.builder(new HttpHost("localhost", 9200)).build()
  implicit val jsonWriter: JsonWriter[Person] = (person: Person) => {
    JsObject(
      "id" -> JsString(person.id),
      "name" -> JsString(person.name),
      "city" -> JsString(person.city))
  }

  val intermediateFlow: Flow[ConsumerRecord[Array[Byte], String], WriteMessage[Person, NotUsed], NotUsed] = Flow[ConsumerRecord[Array[Byte], String]].map { kafkaMessage =>

    val person = Json.parse(kafkaMessage.value()).as[Person]
    val id = person.id

    WriteMessage.createIndexMessage(id, person)
  }

  val esSink: Sink[WriteMessage[Person, NotUsed], Future[Done]] = ElasticsearchSink.create[Person](
    indexName = "sink-person",
    typeName = "person"
  )


  kafkaPlainSource
    .via(intermediateFlow)
    .runWith(esSink)
}
