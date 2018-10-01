package pl.azarnow.scalaWithCats.typeClass

sealed trait Json
final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
case object JsNull extends Json

// interface object
object Json {
  def toJson[T](value: T)(implicit writer: JsonWriter[T]): Json =
    writer.write(value)
}

// interface syntax
object JsonSyntax {
  implicit class JsonWriterOps[T](value: T) {
    // extension method
    def toJson(implicit writer: JsonWriter[T]): Json =
      writer.write(value)
  }
}