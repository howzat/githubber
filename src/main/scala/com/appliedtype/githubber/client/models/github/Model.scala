package com.appliedtype.githubber.client.models.github

import org.http4s.EntityDecoder

object Model {

  object GitHubUserDetails {
    implicit def githubUserDetails[F[_]]: EntityDecoder[F, GitHubUserDetails] = {
      ???
    }
  }

  case class GitHubUserDetails(userId:String)
}
