package com.appliedtype.githubber.client.models.github

import play.api.libs.json.{JsResult, Json, OFormat, _}
import play.api.libs.ws.BodyReadable
import play.api.libs.ws.ahc.StandaloneAhcWSResponse

object Model {

  case class GitHubUserDetails(id: Long, url: String, gistsUrl: String, organizationsUrl: String, reposUrl: String, name: String, company: String, email: String, bio: String)

  object GitHubUserDetails {
    implicit val format: OFormat[GitHubUserDetails] = Json.format[GitHubUserDetails]

    implicit val fooBodyReadable = BodyReadable[GitHubUserDetails] { response =>
      import play.shaded.ahc.org.asynchttpclient.{Response => AHCResponse}
      val ahcResponse = response.asInstanceOf[StandaloneAhcWSResponse].underlying[AHCResponse]

      val jsonBody = Json.parse(ahcResponse.getResponseBody)
//      println(Json.prettyPrint(jsonBody))
      parse(jsonBody).get
    }

    private def parse(json: JsValue): JsResult[GitHubUserDetails] = {

      def getString(key: String) =
        (json \ key).validate[String]
          .fold[JsResult[String]](_ => JsSuccess("Not Set"), v => JsSuccess(v))

      def getInt(key: String) = (json \ key).validate[Int]

      for {
        login <- getString("login")
        id <- getInt("id")
        url <- getString("url")
        gists <- getString("gists_url")
        orgUrl <- getString("organizations_url")
        repos <- getString("repos_url")
        name <- getString("name")
        company <- getString("company")
        email <- getString("email")
        bio <- getString("bio")
      } yield {
        GitHubUserDetails(id, url, gists, orgUrl, repos, name, company, email, bio)
      }
    }
  }
}
