package pl.azarnow.scalaWithCats.printableLibrary

trait Printable[T] {
  def format(obj: T): String
}

object Printable {
  def format[T](value: T)(implicit printable: Printable[T]): String = printable.format(value)

  def print[T](value: T)(implicit printable: Printable[T]): Unit = println(printable.format(value))
}

object PrintableInstances {
  implicit val printableString = new Printable[String] {
    override def format(obj: String): String = obj
  }

  implicit val printableInt = new Printable[Int] {
    override def format(obj: Int): String = obj.toString
  }

  implicit val printableCat = new Printable[Cat] {
    override def format(cat: Cat): String =
      s"${Printable.format(cat.name)} is a ${Printable.format(cat.age)} year-old ${Printable.format(cat.color)} cat."
  }
}

object PrintableSyntax {

  implicit class PrintableOps[T](value: T) {
    def format(implicit printable: Printable[T]): String = printable.format(value)
    def print(implicit printable: Printable[T]): Unit = Printable.print(value)
  }

}
