ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.10"

lazy val root = (project in file("."))
  .settings(
    name := "apollo"
  )

libraryDependencies ++= Seq(
  "com.softwaremill.sttp.client3" %% "zio" % "3.8.11",
  "nu.validator.htmlparser" % "htmlparser" % "1.4",
  "org.scala-lang.modules" %% "scala-xml" % "2.1.0",
  "org.typelevel" %% "mouse" % "1.2.1",
  "org.typelevel" %% "cats-core" % "2.9.0",
  "io.circe" %% "circe-core" % "0.14.4",
  "io.circe" %% "circe-generic" % "0.14.4",
  "io.circe" %% "circe-parser" % "0.14.4",
  "com.datastax.spark" %% "spark-cassandra-connector" % "3.2.0"
)
