package pl.azarnow.scalaWithCats.monad

import cats.data.Reader
import cats.syntax.applicative._
import org.scalatest.{FlatSpec, Matchers}

case class Db(usernames: Map[Int, String], passwords: Map[String, String])

object DbReaderObj {
  type DbReader[A] = Reader[Db, A]

  def findUsername(userId: Int): DbReader[Option[String]] =
    Reader(map => map.usernames.get(userId))

  def checkPassword(username: String,
                    password: String): DbReader[Boolean] =
    Reader(map => map.passwords.get(username).contains(password))

  def checkLogin(
                  userId: Int,
                  password: String): DbReader[Boolean] =
    for {
      username <- findUsername(userId)
      isOk <- username.map { username => checkPassword(username, password) }.getOrElse(false.pure[DbReader])
    } yield isOk
}

class ReaderTest extends FlatSpec with Matchers {

  val users = Map(
    1 -> "dade",
    2 -> "kate",
    3 -> "margo"
  )

  val passwords = Map(
    "dade" -> "zerocool",
    "kate" -> "acidburn",
    "margo" -> "secret"
  )

  val db = Db(users, passwords)

  it should "check logins" in {
    DbReaderObj.checkLogin(1, "zerocool").run(db) shouldEqual true
    DbReaderObj.checkLogin(4, "davinci").run(db) shouldEqual false
  }

}
