package pl.azarnow.scalaWithCats.monad

import cats.data.Writer
import cats.instances.vector._ // for Monoid
import cats.syntax.applicative._ // for pure

import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class FactorialTest extends FlatSpec with Matchers {

  def slowly[A](body: => A) =
    try body finally Thread.sleep(100)

  def factorial(n: Int): Int = {
    type Logged[A] = Writer[Vector[String], A]

    def fact(n: Logged[Int]): Logged[Int] = {
      val ans = slowly(if (n.value == 0) n.map(_ => 1) else fact(n.map(_ - 1)).map(_ * n.value))
      ans.mapWritten(v => v :+ s"fact ${n.value} ${ans.value}")
    }

    val (written, value) = fact(n.pure[Logged]).run
    println(written.mkString("\n"))
    println
    value
  }

  "Factorial" should "compute factorial" in {
    Await.result(Future.sequence(Vector(
      Future(factorial(3)),
      Future(factorial(5))
    )), 5.seconds)
  }

}
