package com.appliedtype.githubber.runner

import cats.Monad
import cats.implicits._
import com.appliedtype.githubber.runner.Commands._
import com.appliedtype.githubber.runner.config.ConsoleConfig
import org.jline.reader.LineReader
import org.jline.terminal.Terminal
import org.jline.utils.AttributedString
import org.jline.utils.InfoCmp.Capability

case class Console[M[_]](
  terminal: Terminal,
  lineReader: LineReader,
  config: ConsoleConfig
) (implicit M: Monad[M]) {

  def read: Command = readLine match {
    case "projects" :: org :: Nil => ProjectsCommand(org)
    case "user" :: name :: Nil => UserInfoCommand(name)
    case "settings" :: Nil => SettingsCommand
    case "quit" :: Nil => Quit
    case "cls" :: Nil => ClearConsole
    case unknown => NoSuchCommand(unknown.mkString(""))
  }

  private def readLine: List[String] = {
    lineReader.readLine().strip().split(' ').map(_.trim) toList
  }

  def respond(resultM: M[CommandResult]): Unit = {
    resultM map { result =>
      val content = AttributedString.fromAnsi(result.stringValue)
      terminal.writer().println(content.toAnsi(terminal))
      terminal.flush()
    }
  }

  def bye(): Unit = {
    val content = AttributedString.fromAnsi("Bye!")
    terminal.writer().println(content.toAnsi(terminal))
    terminal.flush()
    terminal.close()
  }

  def clear(): Unit = {
    terminal.puts(Capability.clear_screen)
    terminal.flush()
  }
}
