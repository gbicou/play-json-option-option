
name := """play-json-option-option"""

scalaVersion := "2.11.8"

organization := "com.bicou"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.5.9" % "provided",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

crossScalaVersions := Seq("2.11.8", "2.10.6")

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

