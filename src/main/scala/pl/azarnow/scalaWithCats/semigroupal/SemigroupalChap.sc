
//trait Semigroupal[F[_]] {
//  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]
//}

import cats.Semigroupal
import cats.instances.option._ // for Semigroupal


Semigroupal[Option].product(Some(123), Some("abc"))




Semigroupal[Option].product(None, Some("abc"))


Semigroupal[Option].product(Some(123), None)

Semigroupal.tuple3(Option(1), Option(2), Option(3))

Semigroupal.tuple3(Option(1), Option(2), Option.empty[Int])













Semigroupal.map3(Option(1), Option(2), Option(3))(_ + _ + _)

Semigroupal.map2(Option(1), Option.empty[Int])(_ + _)






import cats.syntax.apply._     // for tupled and mapN

(Option(1234), Option("abc")).tupled

(Option(123), Option("abc"), Option(true)).tupled










case class Cat1(name: String, born: Int, color: String)

(Option("Garfield"), Option(1978), Option("Orange & black"))
  .mapN(Cat1.apply)


val add: (Int, Int) => Int = (a, b) => a + b
//(Option(1), Option(2), Option(3)).mapN(add) // error
//(Option("cats"), Option(true)).mapN(add) error










import cats.instances.future._ // for Semigroupal
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.higherKinds

val futurePair = Semigroupal[Future].
  product(Future("Hello"), Future(123))

Await.result(futurePair, 1.second)

import cats.syntax.apply._ // for mapN

case class Cat(name: String,
                yearOfBirth: Int,
                favoriteFoods: List[String])

val futureCat = (
  Future("Garfield"),
  Future(1978),
  Future(List("Lasagne"))
).mapN(Cat.apply)

Await.result(futureCat, 1.second)
// res4: Cat = Cat(Garfield,1978,List(Lasagne))


import cats.instances.list._ // for Semigroupal
Semigroupal[List].product(List(1, 2), List(3, 4))


import cats.instances.either._ // for Semigroupal
type ErrorOr[A] = Either[Vector[String], A]
Semigroupal[ErrorOr].product(
  Left(Vector("Error 1")),
  Left(Vector("Error 2"))
)
