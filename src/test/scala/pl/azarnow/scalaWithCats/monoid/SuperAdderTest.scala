package pl.azarnow.scalaWithCats.monoid

import org.scalatest.{FlatSpec, MustMatchers}

class SuperAdderTest extends FlatSpec with MustMatchers {

  "SuperAdder" must "add list of ints" in {
    import cats.instances.int._

    var numbers = List(1, 2, 3, 4, 5)

    val sum = new SuperAdder add numbers

    sum mustEqual 15
  }

  "SuperAdder" must "add list of Optional doubles" in {
    import cats.instances.double._
    import cats.instances.option._

    var numbers: List[Option[Double]] = List(Some(2.2), Some(4.1), Some(5.3))

    val sum = new SuperAdder add numbers

    sum mustEqual Some(11.6)
  }

  "SuperAdder" must "add list of orders" in {
    import Order.orderMonoid

    var orders = List(Order(1.5, 2.0), Order(2.7, 0.5), Order(20.0, 10.0))

    val sum = new SuperAdder add orders

    sum mustEqual Order(24.2, 12.5)
  }

}
