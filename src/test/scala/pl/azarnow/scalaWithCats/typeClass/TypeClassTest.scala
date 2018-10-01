package pl.azarnow.scalaWithCats.typeClass

import org.scalatest.{FlatSpec, Matchers}

class TypeClassTest extends FlatSpec with Matchers {

  "Person" should "be converted with interface object" in {
    val john = Person("john", "mail@buziaczek.pl")

    import JsonWriterInstances._
    val jsonJohn = Json.toJson(john)

    jsonJohn should be(JsObject(Map(
      "name" -> JsString("john"),
      "email" -> JsString("mail@buziaczek.pl")
    )))
  }

}
