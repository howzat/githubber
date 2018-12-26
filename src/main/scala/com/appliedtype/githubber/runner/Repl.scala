package com.appliedtype.githubber.runner

import cats.~>
import com.appliedtype.githubber.runner.Commands.{ClearConsole, CommandResult, Quit}

case class Repl[F[_], R[_]](
  private val console:Console[R],
  private val dispatcher:CommandDispatcher[F],
) (implicit transform: F ~> R) {


  def startRepl(): Unit = {

    var running = true

    while(running) {
      console.read match {
        case Quit             => running = false
        case ClearConsole     => console.clear()
        case gitHubberCommand => {
          val response: F[CommandResult] = dispatcher.dispatch(gitHubberCommand)
          console.respond(transform(response))
        }
      }
    }

    console.bye()
  }
}
