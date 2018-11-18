package pl.azarnow.scalaWithCats.monad

import org.scalatest.{FlatSpec, Matchers}

class IdOpsTest extends FlatSpec with Matchers {

  "Id" should "implement pure" in {
    import pl.azarnow.scalaWithCats.monad.IdOps.pure

    pure(123) shouldEqual 123
  }

  it should "implement map" in {
    import IdOps.map

    map(123)(_ * 2) shouldEqual 246
  }

  it should "implement flatMap" in {
    import IdOps.flatMap

    flatMap(123)(_ * 2) shouldEqual 246
  }

}
