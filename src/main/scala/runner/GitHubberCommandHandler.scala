package runner

import runner.model.{CommandResult, Info}
import cats.MonadError
import cats.data.EitherT
import cats.syntax.applicativeError._
import cats.syntax.apply._
import cats.syntax.either._
import cats.syntax.flatMap._
import cats.syntax.functor._


case class GitHubberCommandHandler[F[_]]()( implicit F: MonadError[F, Throwable]) {

  def handle(input:Vector[String]): F[CommandResult] = {
    F.pure(Info)
  }
}
