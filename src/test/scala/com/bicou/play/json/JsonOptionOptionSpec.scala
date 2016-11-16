package com.bicou.play.json

import org.scalatest._

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class A(id: Option[Option[Int]], name: Option[Option[String]])

class JsonOptionOptionSpec extends WordSpec with Matchers {

  "readOptionOption" should {

    implicit val aReads: Reads[A] = (
      (__ \ 'id).readOptionOption[Int] and
      (__ \ 'name).readOptionOption[String]
    )(A.apply _)

    "read defined as Some(Some)" in {
      Json.parse("""{"id": 1, "name": "Ben"}""").validate[A].get should be(
        A(id = Some(Some(1)), name = Some(Some("Ben")))
      )
    }

    "read null as Some(None)" in {
      Json.parse("""{"id": null, "name": null}""").validate[A].get should be(
        A(id = Some(None), name = Some(None))
      )
    }

    "read missing as None" in {
      Json.parse("""{"name": "Ben"}""").validate[A].get should be(
        A(id = None, name = Some(Some("Ben")))
      )
    }

  }

  "writeOptionOption" should {

    implicit val aWrites: Writes[A] = (
      (__ \ 'id).writeOptionOption[Int] and
      (__ \ 'name).writeOptionOption[String]
    )(unlift(A.unapply))

    "write Some(Some) as defined" in {
      Json.toJson(A(id = Some(Some(1)), name = Some(Some("Ben")))) should be(
        Json.obj("id" -> 1, "name" -> "Ben")
      )
    }

    "write Some(None) as null" in {
      Json.toJson(A(id = Some(None), name = Some(None))) should be(
        Json.obj("id" -> JsNull, "name" -> JsNull)
      )
    }

    "write None as missing" in {
      Json.toJson(A(id = None, name = Some(Some("Ben")))) should be(
        Json.obj("name" -> "Ben")
      )
    }

  }

  "formatOptionOption" should {

    implicit val aFormat: Format[A] = (
      (__ \ 'id).formatOptionOption[Int] and
      (__ \ 'name).formatOptionOption[String]
    )(A.apply, unlift(A.unapply))

    "read / write" in {

      val a = A(id = Some(None), name = Some(Some("Ben")))

      Json.toJson(a) should be(Json.obj("id" -> JsNull, "name" -> "Ben"))

      val js = """{"id": null, "name": "Ben"}"""

      Json.parse(js).validate[A].get should be(a)
    }
  }
}
