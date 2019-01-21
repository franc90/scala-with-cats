import cats.Monad
import cats.syntax.functor._
import cats.syntax.flatMap._
import scala.language.higherKinds

def product[M[_] : Monad, A, B](x: M[A], y: M[B]): M[(A, B)] =
  for {
    a <- x
    b <- y
  } yield (a, b)


import cats.instances.list._

product(List(1, 2), List(3, 4))