package runner

import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.reader.impl.DefaultHighlighter
import org.jline.reader.impl.completer.StringsCompleter
import org.jline.terminal.Terminal
import runner.model.CommandResult

import scala.util.Try

case class GithubberRepl[F[_]](
  private val console:GitHubberConsole[F],
  private val handler:GitHubberCommandHandler[F]
) {

  def startRepl: Unit = {
    while(true) {
      console.readCommand()
        .map(inputArgs => console.respond(handler.handle(inputArgs)))
        .recover {
          case e => ??? //console.respond(GithubberReplHandlingFailure)
        }
    }
  }

  val GithubberReplHandlingFailure = "Oh noes!!"
}
