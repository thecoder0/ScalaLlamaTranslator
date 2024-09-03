ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

lazy val dependencies = Seq(
  "io.github.ollama4j" % "ollama4j" % "1.0.79",
  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "com.typesafe" % "config" % "1.4.2",
  "ch.qos.logback" % "logback-classic" % "1.4.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"
)

lazy val root = (project in file("."))
  .settings(
    libraryDependencies ++= dependencies,
    name := "ScalaLLMTranslator",
    idePackagePrefix := Some("com.natalia")
  )
