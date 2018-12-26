package com.appliedtype.githubber.runner

import cats.effect.IO
import cats.{Id, ~>}
import com.appliedtype.githubber.client.models.github.Model.GitHubUserDetails
import com.appliedtype.githubber.client.models.http.Model.Request
import com.appliedtype.githubber.client.GithubV3ApiClient
import com.appliedtype.githubber.runner.config.GitHubberConfig
import org.http4s.Uri
import org.http4s.client.blaze.Http1Client
import org.jline.reader.LineReader.Option
import org.jline.reader.impl.DefaultParser
import org.jline.reader.impl.completer.StringsCompleter
import org.jline.reader.{LineReader, LineReaderBuilder}
import org.jline.terminal.TerminalBuilder

object Main extends App {

  private val config = GitHubberConfig
  private val httpClient = Http1Client[IO]().unsafeRunSync
  private val reader: LineReader = buildLineReader

  implicit def asyncToId: IO ~> Id = new (IO ~> Id) {
    def apply[A](io:IO[A]): A = {
      io.unsafeRunSync()
    }
  }

  private val getUserInfo : Request[IO, Uri, GitHubUserDetails] = httpClient.expect[GitHubUserDetails]
  private val client  = GithubV3ApiClient(getUserInfo, config)

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
