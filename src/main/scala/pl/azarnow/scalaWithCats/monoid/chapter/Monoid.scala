package pl.azarnow.scalaWithCats.monoid.chapter

private[monoid] trait Semigroup[A] {
  def combine(x: A, y: A): A
}

private[monoid] trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

private[monoid] object Monoid {
  def apply[A](implicit monoid: Monoid[A]) =
    monoid
}