import sbt.Keys._
import sbt.{ModuleID, _}

object Dependencies {

  val http4sVersion = "0.18.21"

  def appDependencies: Seq[Setting[_]] = Seq(libraryDependencies ++= compile ++ testing("test"))

  def testing(scope: String): Seq[ModuleID] = Seq(
    "org.scalamock" %% "scalamock" % "4.1.0" % scope,
    "org.scalacheck" %% "scalacheck" % "1.14.0" % scope
  )

  val http4sDependencies: Seq[ModuleID] = Seq(
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-blaze-client" % http4sVersion
  )

  val catsDependencies: Seq[ModuleID] = Seq(
    "org.typelevel" %% "cats-core" % "1.5.0",
    "org.typelevel" %% "cats-effect" % "1.1.0",
    "io.chrisdavenport" %% "cats-par" % "0.2.0"
  )

  val compile: Seq[ModuleID] = Seq(
    "io.lemonlabs" %% "scala-uri" % "1.4.0",
    "org.jline" % "jline" % "3.9.0" withSources(),
    "com.typesafe" % "config" % "1.3.3",
    "com.iheart" %% "ficus" % "1.4.3"

  ) ++
    http4sDependencies ++
    catsDependencies
}