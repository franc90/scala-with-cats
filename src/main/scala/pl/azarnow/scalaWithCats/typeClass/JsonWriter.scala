package pl.azarnow.scalaWithCats.typeClass

// type class
trait JsonWriter[T] {
  def write(value: T): Json
}

// instances of type class JsonWriter
object JsonWriterInstances {
  implicit val stringWriter: JsonWriter[String] =
    (value: String) => JsString(value)

  implicit val personWriter: JsonWriter[Person] =
    (value: Person) => JsObject(Map(
      "name" -> JsString(value.name),
      "email" -> JsString(value.email)
    ))
}
