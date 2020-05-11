package com.knoldus

import com.knoldus.Sinks.kafkaPlainSink
import com.knoldus.Sources.kafkaCommittableSource
import com.knoldus.utility.ActorJob
import org.apache.kafka.clients.producer.ProducerRecord

import scala.concurrent.ExecutionContextExecutor

object KafkaToKafka extends App with ActorJob {


  implicit val ec: ExecutionContextExecutor = system.dispatcher

  kafkaCommittableSource
    .map(record => new ProducerRecord[String, String]("topic-output", record.record.value()))
    .runWith(kafkaPlainSink)


}
