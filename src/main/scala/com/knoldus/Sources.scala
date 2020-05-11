package com.knoldus

import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerMessage, ConsumerSettings, Subscriptions}
import akka.stream.scaladsl.Source
import com.knoldus.utility.ActorJob
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer}

object Sources extends ActorJob {

  val consumerStringSettings: ConsumerSettings[String, String] =
    ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("alpakka-demo")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val consumerByteSettings: ConsumerSettings[Array[Byte], String] = ConsumerSettings(system, new ByteArrayDeserializer, new StringDeserializer)
    .withBootstrapServers("localhost:9092")
    .withGroupId("alpakka-demo")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
  /**
   * a plainsource for reading from a kafka topic
   */
  val kafkaPlainSource: Source[ConsumerRecord[Array[Byte], String], Consumer.Control] =
    Consumer.plainSource(consumerByteSettings, Subscriptions.topics("topic-person"))

  val kafkaCommittableSource: Source[ConsumerMessage.CommittableMessage[String, String], Consumer.Control] = Consumer
    .committableSource(consumerStringSettings, Subscriptions.topics("topic-input"))
}
