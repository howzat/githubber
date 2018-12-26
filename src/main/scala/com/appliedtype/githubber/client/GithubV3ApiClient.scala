package com.appliedtype.githubber.client

import com.appliedtype.githubber.client.models.github.Model.GitHubUserDetails
import com.appliedtype.githubber.client.models.http.Model.Request
import org.http4s.Uri
import com.appliedtype.githubber.runner.config.GitHubConfig

case class GithubV3ApiClient[F[_]](fetch: Request[F, Uri, GitHubUserDetails], config: GitHubConfig) {

  private lazy val baseUri = Uri.unsafeFromString(config.baseUrl)

  private def userDetailsUrl(): Uri = baseUri
    .withPath("/user/howzat")
    .withQueryParam("access_token" , config.apiToken)

  def getUserDetails(username:String) : F[GitHubUserDetails] = fetch(userDetailsUrl())
}

