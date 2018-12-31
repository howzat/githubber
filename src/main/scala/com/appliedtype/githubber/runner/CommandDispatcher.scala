package com.appliedtype.githubber.runner

import cats.MonadError
import cats.implicits._
import com.appliedtype.githubber.client.GithubV3ApiClient
import com.appliedtype.githubber.runner.Commands._
import com.appliedtype.githubber.runner.config.CommandConfig

case class CommandDispatcher[F[_]](
  gitHubberClient: GithubV3ApiClient[F],
  commandConfig: CommandConfig)(implicit F: MonadError[F, Throwable]) {

  def dispatch(input:Command): F[CommandResult] = input match {
    case UserInfoCommand => gitHubberClient.getUserDetails().map(r => StringResult(r.toString))
    case _ => F.pure(EchoResult(input))
  }
}