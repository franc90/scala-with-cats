name := "scala-with-cats"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.0.0",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"

)

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Ypartial-unification"
)
