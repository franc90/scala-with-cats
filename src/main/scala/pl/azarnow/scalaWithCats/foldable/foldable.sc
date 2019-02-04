

List(1, 2, 3).foldLeft(0)(_ + _)

List(1, 2, 3).foldRight(0)(_ + _)

List(1, 2, 3).foldLeft(0)(_ - _)

List(1, 2, 3).foldRight(0)(_ - _)


List(1, 2, 3).foldRight(List.empty[Int])((x, list) => x :: list)
List(1, 2, 3).foldLeft(List.empty[Int])((list, x) => x :: list)

def mapL[T, U](list: List[T], fn: T => U): List[U] = {
  list.foldLeft(List.empty[U])((acc, x) => acc ++ List(fn(x)))
}

def map[T, U](list: List[T], mapper: T => U): List[U] = {
  list.foldRight(List.empty[U])((x, acc) => mapper(x) :: acc)
}

def flatMapL[T, U](list: List[T], fn: T => List[U]): List[U] = {
  list.foldLeft(List.empty[U])((acc, x) => acc ++ fn(x))
}

def flatMap[T, U](list: List[T], fn: T => List[U]): List[U] = {
  list.foldRight(List.empty[U])((x, acc) => fn(x) ++ acc)
}

def filterL[T](list: List[T], predicate: T => Boolean): List[T] = {
  list.foldLeft(List.empty[T])((acc, x) =>
    if (predicate(x)) acc ++ List(x) else acc)
}

def filter[T](list: List[T], predicate: T => Boolean): List[T] = {
  list.foldRight(List.empty[T])((x, acc) =>
    if (predicate(x)) x :: acc else acc)
}

def sumL(list: List[Int]): Int =
  list.foldLeft(0)(_ + _)

def sum(list: List[Int]): Int =
  list.foldRight(0)(_ + _)


val list: List[Int] = List(1, 2, 3, 4, 5)

val regularMap = list.map(_ * 2)
val leftMap = mapL[Int, Int](list, _ * 2)
val rightMap = map[Int, Int](list, _ * 2)

assert(regularMap == leftMap)
assert(regularMap == rightMap)

val regularFlatMap = list.flatMap(_.toString :: Nil)
val leftFlatMap = flatMapL[Int, String](list, _.toString :: Nil)
val rightFlatMap = flatMap[Int, String](list, _.toString :: Nil)

assert(regularFlatMap == leftFlatMap)
assert(regularFlatMap == rightFlatMap)

val regularFilter = list.filter(_ % 2 == 0)
val leftFilter = filterL[Int](list, _ % 2 == 0)
val rightFilter = filter[Int](list, _ % 2 == 0)

assert(regularFilter == leftFilter)
assert(regularFilter == rightFilter)

val regularSum = list.sum
val leftSum = sumL(list)
val rightSum = sum(list)

assert(regularSum == leftSum)
assert(regularSum == rightSum)




///////////////// FOLDABLE ///////////////////

import cats.instances.all._
import cats.{Eval, Foldable}

val ints = List(1, 2, 3)
Foldable[List].foldLeft(ints, 0)(_ + _)

val maybeInt = Option(123)
Foldable[Option].foldLeft(maybeInt, 10)(_ * _)


def bigData = (1 to 100000).toStream

//bigData.foldRight(0L)(_ + _)
// java.lang.StackOverflowError ...

val eval: Eval[Long] =
  Foldable[Stream].foldRight(bigData, Eval.now(0L)) {
    (num, eval) => eval.map(_ + num)
  }

eval.value

/////// with monoid

Foldable[Option].nonEmpty(Option(42))

Foldable[List].find(ints)(_ % 2 == 0)

Foldable[List].combineAll(ints)

val multipleInts = List(Vector(1,2,3),Vector(4,5,6))

(Foldable[List] compose Foldable[Vector]).combineAll(multipleInts)


import cats.syntax.all._

List(1,2,3).combineAll

List(1,2,3).foldMap(_.toString)