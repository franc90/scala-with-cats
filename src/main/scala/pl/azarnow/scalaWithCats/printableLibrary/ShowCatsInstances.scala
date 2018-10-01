package pl.azarnow.scalaWithCats.printableLibrary

import cats._

object ShowCatsInstances {

  implicit val catShow: Show[Cat] =
    Show.show(
      cat => s"${cat.name} is a ${cat.age} year-old ${cat.color} cat."
    )

}
