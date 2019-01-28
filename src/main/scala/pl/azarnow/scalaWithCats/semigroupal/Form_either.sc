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
type FailFast[A] = Either[List[String], A]

def readValue(field: String, form: FormData): FailFast[String] =
  Either.fromOption(form.get(field), List(s"missing $field"))

def parseInt(name: String)(value: String): FailFast[Int] =
  Either.catchOnly[NumberFormatException](value.toInt)
    .leftMap(_ => List(s"'$name'='$value' is not an int"))

def nonBlank(name: String)(value: String): FailFast[String] =
  Either.cond(value.trim.nonEmpty, value, List(s"'$name' is blank"))

def nonNegative(name: String)(value: Int): FailFast[Int] =
  Either.cond(value >= 0, value, List(s"'$name' is negative"))

def readName(form: FormData): FailFast[String] =
  for {
    value <- readValue("name", form)
    nonBlank <- nonBlank("name")(value)
  } yield nonBlank

def readAge(form: FormData): FailFast[Int] =
  for {
    value <- readValue("age", form)
    age <- parseInt("age")(value)
    nonNegative <- nonNegative("age")(age)
  } yield nonNegative

def readUser(form: FormData): FailFast[User] = {
  Semigroupal[FailFast].product(
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