package pl.azarnow.scalaWithCats.monoid.chapter

object SetOps {
  implicit def intersection[T] = new Semigroup[Set[T]] {
    override def combine(x: Set[T], y: Set[T]): Set[T] = x intersect y
  }

  implicit def union[T] = new Monoid[Set[T]] {
    override def empty: Set[T] = Set.empty

    override def combine(x: Set[T], y: Set[T]): Set[T] = x ++ y
  }

  implicit def symmetricDifference[T] = new Monoid[Set[T]] {
    override def combine(x: Set[T], y: Set[T]): Set[T] = (x -- y) ++ (y -- x)

    override def empty: Set[T] = Set.empty
  }
}
