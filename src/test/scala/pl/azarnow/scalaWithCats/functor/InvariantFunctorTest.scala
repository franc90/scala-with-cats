package pl.azarnow.scalaWithCats.functor

import org.scalatest.{FlatSpec, Matchers}
import pl.azarnow.scalaWithCats.functor.printable.Box

class InvariantFunctorTest extends FlatSpec with Matchers {

  "Codec" should "work with doubles" in {
    import pl.azarnow.scalaWithCats.functor.printable.Codec._

    encode(123.4) should equal("123.4")

    decode[Double]("123.4") should equal(123.4)
  }

  it should "work with Box[A]" in {
    import pl.azarnow.scalaWithCats.functor.printable.Codec._
    encode(Box(123.4)) should equal("123.4")

    decode[Box[Double]]("123.4") should equal(Box(123.4))
  }
}
