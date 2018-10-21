name := "scala-with-cats"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.4.0",
  "org.typelevel" %% "cats-testkit" % "1.4.0" % "test",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"

)

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Ypartial-unification"
)
