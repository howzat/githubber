package com.appliedtype.githubber.client

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import cats.effect.IO
import com.appliedtype.githubber.client.models.github.Model.GitHubUserDetails
import com.appliedtype.githubber.client.models.http.Model.{Request => HttpRequest}
import io.lemonlabs.uri.Url
import play.api.libs.ws.ahc._

case class HttpRequests[F[_]]() {

  import scala.concurrent.ExecutionContext.Implicits._

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  system.registerOnTermination {
    System.exit(0)
  }

  val wsClient = StandaloneAhcWSClient()

  private lazy val headers    = List(
    "applied-type.com" -> 9999,
    "Accept" -> "application/vnd.github.v3+json",
    "User-Agent" -> "howzat"
  )

  val getUserInfo : HttpRequest[IO, Url, GitHubUserDetails] = {
    url =>
      val eventualDetails = wsClient.url(url.toString).get().map(_.body[GitHubUserDetails])
      IO.fromFuture[GitHubUserDetails](IO(eventualDetails))
  }
}