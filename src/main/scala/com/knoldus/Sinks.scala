package com.knoldus

import akka.Done
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.Sink
import com.knoldus.utility.ActorJob
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer

import scala.concurrent.Future

object Sinks extends ActorJob {
  val producerSettings: ProducerSettings[String, String] =
    ProducerSettings(system, new StringSerializer, new StringSerializer)
      .withBootstrapServers("localhost:9092")
  val kafkaPlainSink: Sink[ProducerRecord[String, String], Future[Done]] = Producer.plainSink(producerSettings)
}
