package com.appliedtype.githubber.provider

import com.appliedtype.githubber.{BuildInfo, TestUtils}
import com.itv.scalapact.ScalaPactVerify._
import com.itv.scalapact.circe09._
import com.itv.scalapact.http4s18._
import com.itv.scalapact.shared.{BrokerPublishData, ProviderStateResult}
import com.itv.scalapactcore.verifier.ProviderState
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.duration._

class ProviderVerificationSpec extends WordSpec with Matchers  {

  private val userJson: String = TestUtils.readFile("/github-user-response.json")

  verifyPact
    .withPactSource(pactBrokerUseLatest(
      BuildInfo.pactBrokerAddress,
      "github",
      List("githubber"),
      Some(BrokerPublishData(BuildInfo.version, Some(BuildInfo.pactBrokerAddress))),
      None
    ))
    .setupProviderState("given") {
      case state if state == "An access token 12345" =>
        ProviderStateResult(true)
      case state =>
        ProviderStateResult(true)
    }
    .runStrictVerificationAgainst("localhost", 80, 5 seconds)
}
