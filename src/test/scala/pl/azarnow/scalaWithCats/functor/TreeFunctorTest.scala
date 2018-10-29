package pl.azarnow.scalaWithCats.functor

import cats.Functor
import cats.syntax.functor._
import org.scalatest.{FlatSpec, Matchers}

import scala.language.higherKinds

class TreeFunctorTest extends FlatSpec with Matchers {

  "TreeFunctor" should "map Leaf[Int] to Leaf[String]" in {
    import TreeFunctor.treeFunctor
    val tree: Tree[Int] = Leaf(12)

    val mappedLeaf = tree.map(_ * 2)

    mappedLeaf should be(Leaf(24))
  }

  it should "map Branch[Int, Int] to Branch[Int, Int]" in {
    import TreeFunctor.treeFunctor
    val tree: Tree[Int] = Branch(Branch(Leaf(1),Leaf(2)),Leaf(3))

    val mappedLeaf = tree.map(_ * 2)

    mappedLeaf should be(Branch(Branch(Leaf(2),Leaf(4)),Leaf(6)))
  }

}
