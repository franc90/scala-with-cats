package pl.azarnow.scalaWithCats.monad

import cats.data.State
import org.scalatest.{FlatSpec, Matchers}

import scala.annotation.tailrec

object Calc {
  type CalcState[A] = State[List[Int], A]

  def isInt(x: String) =
    try {
      x.toInt
      true
    } catch {
      case _: NumberFormatException => false
    }

  def evalOne(sym: String): CalcState[Int] = {
    def create(op: (Int, Int) => Int, name: String): CalcState[Int] = {
      State[List[Int], Int] { stack =>
        stack match {
          case h :: h2 :: t =>
            val computed = op(h2, h)
            (computed :: t, computed)
          case _ => throw new RuntimeException(s"Cannot $name for stack: $stack")
        }
      }
    }

    sym match {
      case "+" => create(_ + _, "add")
      case "-" => create((a, b) => a - b, "sub")
      case "/" => create(_ / _, "div")
      case "*" => create(_ * _, "mul")
      case x if isInt(x) => State[List[Int], Int] { stack =>
        (x.toInt :: stack, x.toInt)
      }
    }
  }

  def evalAll(input: List[String]): CalcState[Int] = {
    @tailrec
    def eval(input: List[String], acc: CalcState[Int]): CalcState[Int] = {
      input match {
        case h :: t =>
          eval(t, acc.flatMap(_ => evalOne(h)))
        case _ => acc
      }
    }

    eval(input, State.pure[List[Int], Int](0))
  }

  def betterEvalAll(input: List[String]): CalcState[Int] = {
    import cats.syntax.applicative._
    input.foldLeft(0.pure[CalcState]) {
      (a, b) => a.flatMap(_ => evalOne(b))
    }
  }

  def evalInput(input: String): Int = {
    val symbols = input.split(" ").toList
    evalAll(symbols).runA(Nil).value
  }

}

class StateTest extends FlatSpec with Matchers {

  import Calc._

  it should "compute 42" in {
    evalOne("42").runA(Nil).value shouldEqual 42
  }

  it should "compute 1+2" in {
    val program = for {
      _ <- evalOne("1")
      _ <- evalOne("2")
      ans <- evalOne("+")
    } yield ans
    program.runA(Nil).value shouldEqual 3
  }

  it should "compute 1+2+3" in {
    val program = for {
      _ <- evalOne("1")
      _ <- evalOne("2")
      _ <- evalOne("+")
      _ <- evalOne("3")
      ans <- evalOne("+")
    } yield ans
    program.runA(Nil).value shouldEqual 6
  }

  it should "compute (1+2)*3+4-5" in {
    val program = evalAll(List("1", "2", "+", "3", "*", "4", "+", "5", "-"))
    program.runA(Nil).value shouldEqual 8
  }

  it should "also compute (1+2)*3" in {
    val program = betterEvalAll(List("1", "2", "+", "3", "*"))
    program.runA(Nil).value shouldEqual 9
  }

  it should "compute 12+33-44" in {
    evalInput("12 33 + 44 -") shouldEqual 1
  }

  it should "compute (1+2)*(3+4)" in {
    evalInput("1 2 + 3 4 + *") shouldEqual 21
  }

}
