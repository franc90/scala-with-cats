import cats.data.EitherT
import cats.instances.future._
import cats.syntax.applicative._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.higherKinds

type Response[A] = EitherT[Future, String, A]

def getPowerLevel(autobot: String): Response[Int] = {
  val powerLevels = Map(
    "Jazz" -> 6,
    "Bumblebee" -> 8,
    "Hot Rod" -> 10
  )

  powerLevels.get(autobot) match {
    case Some(level) =>
      level.pure[Response]
    case None =>
      EitherT[Future, String, Int](Future(Left(s"$autobot is unreachable.")))
  }
}

def canSpecialMove(ally1: String, ally2: String): Response[Boolean] =
  for {
    lvl1 <- getPowerLevel(ally1)
    lvl2 <- getPowerLevel(ally2)
  } yield (lvl1 + lvl2) > 15

def tacticalReport(ally1: String, ally2: String): String =
  Await.result(canSpecialMove(ally1, ally2).value, 1.second) match {
    case Left(msg) => msg
    case Right(true) => s"$ally1 and $ally2 are ready to roll out!"
    case Right(false) => s"$ally1 and $ally2 need a recharge."
  }

tacticalReport("Jazz", "Bumblebee")
tacticalReport("Bumblebee", "Hot Rod")
tacticalReport("Jazz", "Ironhide")