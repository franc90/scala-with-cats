package pl.azarnow.scalaWithCats.printableLibrary

import org.scalatest.{FlatSpec, Matchers}

class ShowCat extends FlatSpec with Matchers {

  "cats.Show" should "convert Cat to readable form" in {
    import ShowCatsInstances._
    import cats.syntax.show._

    val humanReadableCat = Cat("Meow", 5, "blue").show

    humanReadableCat should be("Meow is a 5 year-old blue cat.")
  }

}
