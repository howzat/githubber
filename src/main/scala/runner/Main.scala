package runner

import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.reader.impl.completer.StringsCompleter
import org.jline.terminal.{Terminal, TerminalBuilder}
import scala.concurrent.ExecutionContext.Implicits.global
import cats.instances.future._
import scala.concurrent.Future

object Main extends App {

  private val completer = new StringsCompleter("help", "quit")

  private val terminal: Terminal = TerminalBuilder
    .builder()
    .system(true)
    .build()

  private val lineReader: LineReader = LineReaderBuilder.builder()
    .terminal(terminal)
    .completer(completer)
    .build()

  private val console = GitHubberConsole[Future](terminal, lineReader)
  private val commendHandler = GitHubberCommandHandler[Future]()
  private val repl = GithubberRepl[Future](console, commendHandler)

  repl startRepl
}