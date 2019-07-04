import sbt.Keys._
import sbt.{ModuleID, _}

object Dependencies {

  val playWsStandaloneVersion = "2.0.0-RC2"

  def appDependencies: Seq[Setting[_]] = Seq(libraryDependencies ++= compile ++ testing("test") ++ pactDependencies)

  val pactVersion = "2.3.4"

  val pactDependencies: Seq[ModuleID] = Seq(
    "com.itv" %% "scalapact-circe-0-9" % pactVersion % "test",
    "com.itv" %% "scalapact-http4s-0-18" % pactVersion % "test",
    "com.itv" %% "scalapact-scalatest" % pactVersion % "test" withSources(),
    "org.scalatest" %% "scalatest" % "3.0.5" % "test"
  )


  def testing(scope: String): Seq[ModuleID] = Seq(
    "org.scalamock" %% "scalamock" % "4.1.0" % scope,
    "org.scalacheck" %% "scalacheck" % "1.14.0" % scope,
    "commons-io" % "commons-io" % "2.6"  % scope
  )

  val catsDependencies: Seq[ModuleID] = Seq(
    "org.typelevel" %% "cats-core" % "1.5.0",
    "org.typelevel" %% "cats-effect" % "0.10",
    "io.chrisdavenport" %% "cats-par" % "0.2.0"
  )

  val compile: Seq[ModuleID] = Seq(
    "io.lemonlabs" %% "scala-uri" % "1.4.0",
    "org.jline" % "jline" % "3.9.0" withSources(),
    "com.typesafe" % "config" % "1.3.3",
    "com.iheart" %% "ficus" % "1.4.3",
    "com.typesafe.play" %% "play-ahc-ws-standalone" % playWsStandaloneVersion withSources(),
    "com.typesafe.play" %% "play-ws-standalone-json" % playWsStandaloneVersion
  ) ++
    catsDependencies
}