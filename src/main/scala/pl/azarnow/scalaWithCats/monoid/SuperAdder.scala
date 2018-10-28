package pl.azarnow.scalaWithCats.monoid

import cats.Monoid
import cats.syntax.semigroup._

class SuperAdder {

  def add[T: Monoid](items: List[T]): T = {
    items.foldLeft(Monoid[T].empty)(_ |+| _)
  }

}
