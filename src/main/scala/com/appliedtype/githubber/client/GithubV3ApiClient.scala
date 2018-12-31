package com.appliedtype.githubber.client

import com.appliedtype.githubber.client.models.github.Model.GitHubUserDetails
import com.appliedtype.githubber.client.models.http.Model.Request
import com.appliedtype.githubber.runner.config.GitHubConfig
import io.lemonlabs.uri.Url

case class GithubV3ApiClient[F[_]](fetch: Request[F, Url, GitHubUserDetails], config: GitHubConfig) {

  private val baseUri: Url = Url.parse(config.baseUrl)

  def getUserDetails() : F[GitHubUserDetails] = {

    val request = baseUri
      .addPathPart("user")
      .withQueryString("access_token" -> config.apiToken)

    fetch(request)
  }
}

