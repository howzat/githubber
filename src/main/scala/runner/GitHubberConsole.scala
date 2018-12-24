package runner

import cats.MonadError
import org.jline.reader.LineReader
import org.jline.terminal.Terminal
import runner.model.CommandResult
import cats.MonadError
import cats.data.EitherT
import cats.syntax.applicativeError._
import cats.syntax.apply._
import cats.syntax.either._
import cats.syntax.flatMap._
import cats.syntax.functor._

import scala.util.Try
import org.jline.utils.AttributedString
import runner.config.ReplConfig


case class GitHubberConsole[F[_]](terminal:Terminal, lineReader: LineReader, config:ReplConfig)(implicit F: MonadError[F, Throwable]) {


  def readCommand(): Try[Vector[String]] =  {
    Try(lineReader
      .readLine(config.prompt)
      .split(' ')
      .toVector
      .map(_.trim)
    )
  }

  def respond(result:F[CommandResult]) : Unit = {
    result map { output:CommandResult =>
      val content = AttributedString.fromAnsi("\u001B[33m======>\u001B[0m\"" + output.stringValue + "\"")
      terminal.writer().println(content.toAnsi(terminal))
    }
  }
}
