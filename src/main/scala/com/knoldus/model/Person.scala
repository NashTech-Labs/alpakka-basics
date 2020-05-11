package com.knoldus.model

import play.api.libs.json.{Json, OFormat}

case class Person(id: String, name: String, city: String)

object Person{
  implicit val format: OFormat[Person] = Json.format[Person]
}
