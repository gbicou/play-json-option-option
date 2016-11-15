package com.bicou.play

import _root_.play.api.libs.json._

package object json {

  implicit class OptionOption(path: JsPath) {

    def readOptionOption[A](implicit r: Reads[A]): Reads[Option[Option[A]]] = JsonOptionOption.reads[A](path)(r)

    def writeOptionOption[A](implicit w: Writes[A]): OWrites[Option[Option[A]]] = JsonOptionOption.writes[A](path)(w)

    def formatOptionOption[A](implicit f: Format[A]): OFormat[Option[Option[A]]] = JsonOptionOption.format[A](path)(f)

  }
}

