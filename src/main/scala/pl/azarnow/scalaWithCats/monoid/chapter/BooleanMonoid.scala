package pl.azarnow.scalaWithCats.monoid.chapter

object BooleanMonoid {
  implicit val and = new Monoid[Boolean] {
    override def empty: Boolean = true

    override def combine(x: Boolean, y: Boolean): Boolean = x && y
  }

  implicit val or = new Monoid[Boolean] {
    override def empty: Boolean = false

    override def combine(x: Boolean, y: Boolean): Boolean = x || y
  }

  implicit val xor = new Monoid[Boolean] {
    override def empty: Boolean = false

    override def combine(x: Boolean, y: Boolean): Boolean = (x && !y) || (!x && y)
  }

  implicit val xnor = new Monoid[Boolean] {
    override def empty: Boolean = true

    override def combine(x: Boolean, y: Boolean): Boolean = (!x && y) && (x && !y)
  }

}
