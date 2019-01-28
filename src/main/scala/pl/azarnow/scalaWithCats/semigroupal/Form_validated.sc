// Specification
//
// the name and age must be specified;
// the name must not be blank;
// the age must be a valid non-negative integer.

import cats.Semigroupal
import cats.data.Validated
import cats.syntax.all._
import cats.instances.all._

case class User(name: String, age: Int)

type FormData = Map[String, String]
type FailSlow[A] = Validated[List[String], A]

def readValue(field: String, form: FormData): FailSlow[String] =
  Validated.fromOption(form.get(field), List(s"missing $field"))

def parseInt(name: String)(value: String): FailSlow[Int] =
  Validated.catchOnly[NumberFormatException](value.toInt)
    .leftMap(_ => List(s"'$name'='$value' is not an int"))

def nonBlank(name: String)(value: String): FailSlow[String] =
  Validated.cond(value.trim.nonEmpty, value, List(s"'$name' is blank"))

def nonNegative(name: String)(value: Int): FailSlow[Int] =
  Validated.cond(value >= 0, value, List(s"'$name' is negative"))

def readName(form: FormData): FailSlow[String] =
  readValue("name", form).andThen {
    value => nonBlank("name")(value)
  }

def readAge(form: FormData): FailSlow[Int] =
  readValue("age", form) andThen {
    value => parseInt("age")(value) andThen {
      age => nonNegative("age")(age)
    }
  }

def readUser(form: FormData): FailSlow[User] = {
  Semigroupal[FailSlow].product(
    readName(form),
    readAge(form)
  ).map {
    case (name, age) => User(name, age)
  }
}

readUser(Map("name" -> "Tom", "age" -> "5"))
readUser(Map("name" -> "  ", "age" -> "2"))
readUser(Map("name" -> "John", "age" -> "-12"))
readUser(Map("name" -> "  ", "age" -> "-3"))
readUser(Map("age" -> "-1"))
