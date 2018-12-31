package com.appliedtype.githubber.runner

import cats.effect.IO
import cats.{Id, ~>}
import com.appliedtype.githubber.client.{GithubV3ApiClient, HttpRequests}
import com.appliedtype.githubber.runner.config.GitHubberConfig
import org.jline.reader.LineReader.Option
import org.jline.reader.impl.DefaultParser
import org.jline.reader.impl.completer.StringsCompleter
import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.TerminalBuilder

object Main extends App {

  private val config = GitHubberConfig

  private val reader: LineReader = buildLineReader

  implicit def asyncToId: IO ~> Id = new (IO ~> Id) {
    def apply[A](io:IO[A]): A = {
      io.unsafeRunSync()
    }
  }

  private val client  = GithubV3ApiClient(HttpRequests().getUserInfo, config)
  private val console = Console(buildTerminal, reader, config)
  private val repl    = Repl[IO, Id](console, CommandDispatcher[IO](client, config))(asyncToId)

  repl startRepl()

  private def buildTerminal = {
    TerminalBuilder
      .builder()
      .system(true)
      .nativeSignals(true)
      .build()
  }

  private def buildLineReader = {
    val reader = LineReaderBuilder
      .builder()
      .terminal(buildTerminal)
      .completer(new StringsCompleter(config.commandList: _*))
      .parser(new DefaultParser)
      .appName("GitHubber Repl")
      .build()

    reader.setOpt(Option.MENU_COMPLETE)
    reader.setOpt(Option.AUTO_LIST)
    reader.setOpt(Option.AUTO_MENU)
    reader.setOpt(Option.LIST_AMBIGUOUS)
    reader.setOpt(Option.AUTO_FRESH_LINE)
    reader
  }
}
