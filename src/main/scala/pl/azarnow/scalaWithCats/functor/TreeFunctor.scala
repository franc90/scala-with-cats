package pl.azarnow.scalaWithCats.functor

import cats.Functor

object TreeFunctor {

  implicit val treeFunctor: Functor[Tree] =
    new Functor[Tree] {
      override def map[A, B](tree: Tree[A])(f: A => B): Tree[B] =
        tree match {
          case Branch(left, right) => Branch(map(left)(f), map(right)(f))
          case Leaf(value) => Leaf(f(value))
        }
    }

}
