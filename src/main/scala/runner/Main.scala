package runner

import cats.instances.future._
import org.jline.reader.impl.completer.StringsCompleter
import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.{Terminal, TerminalBuilder}
import runner.config.GitHubberConfig

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Main extends App {

  private val config = GitHubberConfig

  private val completer = new StringsCompleter(config.commandList:_*)

  private val terminal: Terminal = TerminalBuilder
    .builder()
    .system(true)
    .build()

  private val lineReader: LineReader = LineReaderBuilder.builder()
    .terminal(terminal)
    .completer(completer)
    .build()

  private val console = GitHubberConsole[Future](terminal, lineReader, config)
  private val commendHandler = GitHubberCommandHandler[Future]()
  private val repl = GithubberRepl[Future](console, commendHandler)

  repl startRepl
}