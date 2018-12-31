package com.appliedtype.githubber.runner

object Commands {

  sealed trait Command {
    def unapply(inputTokens: Seq[String]): Option[Command] = Some(this)
  }

  case class NoSuchCommand(input: String) extends Command
  case object Quit extends Command
  case object ClearConsole extends Command
  case object SettingsCommand extends Command
  case object UserInfoCommand extends Command
  case class ProjectsCommand(organisation: String) extends Command

  sealed trait CommandResult {
    def stringValue: String
  }

  case class StringResult(text:String) extends CommandResult {
    def stringValue: String = text
  }

  case class EchoResult(command: Command) extends CommandResult {
    override def stringValue: String = command toString
  }
}