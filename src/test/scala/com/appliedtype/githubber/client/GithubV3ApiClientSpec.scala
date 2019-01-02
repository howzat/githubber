package com.appliedtype.githubber.client

import cats.effect.IO
import com.appliedtype.githubber.TestUtils
import com.appliedtype.githubber.client.models.github.Model.GitHubUserDetails
import com.appliedtype.githubber.runner.config.{GitHubConfig, GitHubberConfig}
import com.itv.scalapact.ScalaPactForger._
import com.itv.scalapact.ScalaPactMockConfig
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{Matchers, WordSpec}

class GithubV3ApiClientSpec extends WordSpec with Matchers {

  private val userJson = TestUtils.readFile("/github-user-response.json")

  val config = GitHubberConfig

  import com.itv.scalapact.http._
  import com.itv.scalapact.json._

  forgePact
    .between("githubber")
    .and("github")
    .addInteraction(
      interaction
        .description("Fetch a users details")
        .given("An access token 12345")
        .uponReceiving(method = GET, path = s"/user", query = Some("access_token=12345"))
        .willRespondWith(
          status  = 200,
          headers = Map("Content-Type" -> "application/json"),
          body    = userJson,
          matchingRules = MatchingRules.minimal
        )
    ).runConsumerTest { mockConfig: ScalaPactMockConfig =>

    val config = new GitHubConfig {
      override val configuration: Config = ConfigFactory.empty()
      override lazy val baseUrl: String = mockConfig.baseUrl
      override lazy val apiToken: String = "12345"
    }

    val value = new GithubV3ApiClient[IO](HttpRequests() getUserInfo, config).getUserDetails()

    val userInfo = value.unsafeRunSync()

    userInfo shouldBe GitHubUserDetails(
      920913,
      "https://api.github.com/users/howzat",
      "https://api.github.com/users/howzat/gists{/gist_id}",
      "https://api.github.com/users/howzat/orgs",
      "https://api.github.com/users/howzat/repos",
      "Ben Letton",
      "Applied Type",
      "ben@applied-type.com",
      "Not Set")
  }

  object MatchingRules {
    val minimal: ScalaPactMatchingRules =
      Seq("id","url","gists_url","organizations_url","repos_url","name","company","email","bio")
        .map(rule => bodyTypeRule(rule)).reduce(_ ~> _)
  }
}
