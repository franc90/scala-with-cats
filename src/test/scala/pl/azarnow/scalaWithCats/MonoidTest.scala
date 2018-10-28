package pl.azarnow.scalaWithCats

import cats.Monoid
import org.scalatest.{FlatSpec, Matchers}

class MonoidTest extends FlatSpec with Matchers {

  "Monoid" should "be associative" in {
    import cats.kernel.instances.int.catsKernelStdGroupForInt

    def associativeLaw[A](x: A, y: A, z: A)(implicit m: Monoid[A]): Boolean = {
      m.combine(x, m.combine(y, z)) == m.combine(m.combine(x, y), z)
    }

    associativeLaw(1,2,3) should be(true)
  }
  "Monoid" should "have identity" in {
    import cats.kernel.instances.int.catsKernelStdGroupForInt

    def identityLaw[A](x: A)(implicit m: Monoid[A]): Boolean = {
      m.combine(x, m.empty) == x && m.combine(m.empty, x) == x
    }

    identityLaw(1) should be(true)
  }

}
