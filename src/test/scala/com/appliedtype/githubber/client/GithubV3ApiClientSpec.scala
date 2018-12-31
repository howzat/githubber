package com.appliedtype.githubber.client

import cats.effect.IO
import com.appliedtype.githubber.TestUtils
import com.appliedtype.githubber.client.models.github.Model.GitHubUserDetails
import com.appliedtype.githubber.runner.config.{GitHubConfig, GitHubberConfig}
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{Matchers, WordSpec}
import com.itv.scalapact.ScalaPactForger._

class GithubV3ApiClientSpec extends WordSpec with Matchers {

  private val userJson = TestUtils.readFile("/github-user-response.json")

  val config = GitHubberConfig

  import com.itv.scalapact.json._
  import com.itv.scalapact.http._

  forgeStrictPact
    .between("githubber")
    .and("github")
    .addInteraction(
      interaction
        .description("Fetch a users details")
        .given("An access token 12345")
        .uponReceiving(method = GET, path = s"${config.baseUrl}/user")
        .willRespondWith(
          status  = 200,
          headers = Map("Content-Type" -> "application/json"),
          body    = userJson,
          matchingRules = MatchingRules.minimal
        )
    ).runConsumerTest { c =>

    val value = new GithubV3ApiClient[IO](HttpRequests() getUserInfo, config).getUserDetails()

    val userInfo = value.unsafeRunSync()

    userInfo shouldBe GitHubUserDetails(1234,"1234","1234","1234","1234","1234","1234","1234","1234")
  }

  object MatchingRules {
    val minimal: ScalaPactMatchingRules =
      Seq("id","url","gists_url","organizations_url","repos_url","name","company","email","bio")
        .map(rule => bodyTypeRule(rule)).reduce(_ ~> _)
  }
}
