package com.bicou.play.json

import play.api.libs.json._

package object optionoption {

  implicit class PlayJsonOptionOption(path: JsPath) {

    def readOptionOption[A](implicit r: Reads[A]): Reads[Option[Option[A]]] = OptionOption.reads[A](path)(r)

    def writeOptionOption[A](implicit w: Writes[A]): OWrites[Option[Option[A]]] = OptionOption.writes[A](path)(w)

    def formatOptionOption[A](implicit f: Format[A]): OFormat[Option[Option[A]]] = OptionOption.format[A](path)(f)

  }
}
