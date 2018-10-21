package pl.azarnow.scalaWithCats.printableLibrary

import cats.instances.int._
import cats.instances.string._
import cats.kernel.Eq
import cats.syntax.eq._

object CatEq {

  implicit val catEq: Eq[Cat] =
    Eq.instance[Cat] {
      (c1, c2) =>
        c1.name === c2.name &&
          c1.age === c2.age &&
          c1.color === c2.color
    }

}
