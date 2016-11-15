package com.bicou.play.json

import org.scalatest._

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class A(
  id: Option[Option[Int]],
  name: Option[Option[String]]
)

object A {
  
  implicit val attrsReads: Reads[A] = (
    (__ \ 'id).readOptionOption[Int] and
    (__ \ 'name).readOptionOption[String]
  )(A.apply _)

  implicit val attrsWrites: Writes[A] = (
    (__ \ 'id).writeOptionOption[Int] and
    (__ \ 'name).writeOptionOption[String]
  )(unlift(A.unapply))

}

class JsonOptionOptionSpec extends WordSpec with Matchers {
  
  "readOptionOption" should {

    "read defined as Some(Some)" in {

      val js = """{"id": 1, "name": "Ben"}"""

      Json.parse(js).validate[A].get should be(A(
        id = Some(Some(1)),
        name = Some(Some("Ben"))
      ))
    }

    "read null as Some(None)" in {

      val js = """{"id": null, "name": null}"""

      Json.parse(js).validate[A].get should be(A(
        id = Some(None),
        name = Some(None)
      ))
    }

    "read missing as None" in {

      val js = """{"name": "Ben"}"""

      Json.parse(js).validate[A].get should be(A(
        id = None,
        name = Some(Some("Ben"))
      ))
    }
  
  }

  "writeOptionOption" should {

    "write Some(Some) as defined" in {

      val a = A(
        id = Some(Some(1)),
        name = Some(Some("Ben"))
      )

      Json.toJson(a) should be(Json.obj(
        "id" -> 1,
        "name" -> "Ben"
      ))
    }

    "write Some(None) as null" in {

      val a = A(
        id = Some(None),
        name = Some(None)
      )

      Json.toJson(a) should be(Json.obj(
        "id" -> JsNull,
        "name" -> JsNull
      ))
    }

    "write None as missing" in {

      val a = A(
        id = None,
        name = Some(Some("Ben"))
      )

      Json.toJson(a) should be(Json.obj(
        "name" -> "Ben"
      ))
    }

  }

}

