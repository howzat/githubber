package com.appliedtype.githubber.runner.config

import com.typesafe.config
import com.typesafe.config.{Config, ConfigFactory}
import net.ceedubs.ficus.Ficus._

object GitHubberConfig
  extends ReadConfig
    with GitHubConfig
    with CommandConfig
    with ConsoleConfig {

  override lazy val configuration: config.Config = ConfigFactory.load()
}

sealed trait ReadConfig {
  val configuration: Config

  def getStringList(key:String): Seq[String] = configuration.as[Seq[String]](key)

  def getString(key:String): String = configuration.as[String](key)

  def getStringOpt(key:String): Option[String] = configuration.as[Option[String]](key)

  def fail(key:String, message:String) = throw new RuntimeException(
    s"Configuration Error: [$key][$message]"
  )
}

trait GitHubConfig extends ReadConfig {

  private lazy val apiTokenConfiguration: Config =
    getStringOpt("secrets.conf"). fold(
        fail("secrets.conf", "Couldn't load the secrets required to run GiHubber!")
      )(ConfigFactory.load)

  lazy val apiToken: String = apiTokenConfiguration.getString("github.token")

  lazy val baseUrl: String = configuration.as[String]("github.baseUrl")
}

trait CommandConfig extends ReadConfig {

  lazy val commandList: Seq[String] = getStringList("repl.commandList")
}

trait ConsoleConfig extends ReadConfig {

  lazy val prompt: String =
    getStringOpt("repl.prompt")
      .getOrElse(">")
}