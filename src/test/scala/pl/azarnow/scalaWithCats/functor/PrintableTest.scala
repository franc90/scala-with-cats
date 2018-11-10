package pl.azarnow.scalaWithCats.functor

import org.scalatest.{FlatSpec, Matchers}
import pl.azarnow.scalaWithCats.functor.printable.{Box, Printable}

class PrintableTest extends FlatSpec with Matchers {

  "Printable " should " format both string and boolean" in {
    import Printable._

    format("hello, there") should equal("\"hello, there\"")
    format(true) should equal("yes")
    format(false) should equal("no")
  }

  it should "format Box[A]" in {
    import Printable._

    format(Box("hello world")) should equal("\"hello world\"")
    format(Box(true)) should equal("yes")
  }

}
