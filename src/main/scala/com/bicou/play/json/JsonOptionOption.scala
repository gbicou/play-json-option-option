package com.bicou.play.json

import play.api.libs.json._

object JsonOptionOption {

  def reads[A](path: JsPath)(implicit reads: Reads[A]): Reads[Option[Option[A]]] =
    Reads[Option[Option[A]]] { json =>
      path.applyTillLast(json).fold(
        jserr => jserr,
        jsres => jsres.fold(
          _ => JsSuccess(None),
          a => a match {
            case JsNull => JsSuccess(Some(None))
            case js => reads.reads(js).repath(path).map(v => Some(Some(v)))
          }
        )
      )
    }

  def writes[A](path: JsPath)(implicit wrs: Writes[A]): OWrites[Option[Option[A]]] =
    OWrites[Option[Option[A]]] { a =>
      a match {
        case Some(a) => a match {
          case Some(av) => JsPath.createObj(path -> wrs.writes(av))
          case None => JsPath.createObj(path -> JsNull)
        }
        case None => Json.obj()
      }
    }

  def format[A](path: JsPath)(implicit fmt: Format[A]): OFormat[Option[Option[A]]] = OFormat(reads(path), writes(path))

}
