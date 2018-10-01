package pl.azarnow.scalaWithCats.printableLibrary

import org.scalatest.{FlatSpec, Matchers}

class CatPrinter extends FlatSpec with Matchers {

  "Printable" should "convert cat to readable form" in {
    import PrintableInstances._

    val humanReadableCat = Printable.format(Cat("Meow", 5, "blue"))

    humanReadableCat should be("Meow is a 5 year-old blue cat.")
  }

  "Printable" should "convert cat to readable form with syntax" in {
    import PrintableInstances._
    import PrintableSyntax._

    val humanReadableCat = Cat("Meow", 5, "blue").format

    humanReadableCat should be("Meow is a 5 year-old blue cat.")
  }

}
