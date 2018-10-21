package pl.azarnow.scalaWithCats.printableLibrary

import org.scalatest.{FlatSpec, Matchers}

class CatEqTest extends FlatSpec with Matchers with cats.tests.StrictCatsEquality {

  "Cat" should "be equal to identical cat" in {
    import CatEq._

    val cat1 = Cat("Garfield", 38, "orange and black")
    val cat2 = Cat("Heathcliff", 33, "orange and black")
    val cat3 = Cat("Heathcliff", 33, "orange and black")

    cat1 === cat2 shouldBe false
    cat3 === cat2 should be(true)

    import cats.syntax.eq._
    cat1 =!= cat2 should be(true)
    cat3 =!= cat2 should be(false)
  }

  "Cat options" should "be equal to identical cat" in {
    import CatEq._
    import cats.instances.option._

    val cat1 = Cat("Garfield", 38, "orange and black")
    val cat2 = Cat("Heathcliff", 33, "orange and black")
    val optionCat1 = Option(cat1)
    val optionCat2 = Option.empty[Cat]

    optionCat1 === optionCat2 should be(false)

    import cats.syntax.eq._
    optionCat1 =!= optionCat2 should be(true)
  }

}
