import com.itv.scalapact.plugin.ScalaPactPlugin._
import scala.sys.process._

ThisBuild / version := "0.0.1"
ThisBuild / organization := "com.appliedtype"

val pactUp = taskKey[Unit]("Packs and publishes pact files currently in pact directory")

val pactTags: Seq[String] = List(git.gitCurrentBranch.value, git.baseVersion.value) ++ git.gitCurrentTags.value


lazy val microservice = (project in file("."))
  .enablePlugins(ScalaPactPlugin, GitVersioning)
  .settings(Dependencies.appDependencies: _*)
  .settings(
    name := "githubber",
    unmanagedResourceDirectories in Compile += baseDirectory.value / "resources",
    scalaVersion := "2.12.8",
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-language:higherKinds",
      "-language:postfixOps",
      "-feature",
      "-Ypartial-unification",
      "-Ywarn-dead-code",
      "-Ywarn-value-discard",
      "-Ywarn-inaccessible",
      "-Ywarn-infer-any",
      "-Ywarn-nullary-override",
      "-Ywarn-nullary-unit",
      "-Ywarn-numeric-widen",
      "-Ywarn-unused-import",
      "-Xlint"
    ),
    addCommandAlias("testAll", ";reload;test;pactPack;pactPush"),
    addCommandAlias("pactPublish", ";pactPack;pactPush")
  )
  .settings(evictionWarningOptions in update := EvictionWarningOptions.default.withWarnScalaVersionEviction(true))
  .settings(
    pactBrokerAddress := "http://localhost:80",
    pactContractVersion := "1.0.0",
    providerName := "GitHubber",
    pactContractTags := pactTags,
    pactContractVersion := ("git rev-parse --short HEAD" !!).trim,
    allowSnapshotPublish := true
  )
  .settings(
    resolvers ++= Seq(
      Resolver.jcenterRepo,
      Resolver.typesafeIvyRepo("releases")
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.9"),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.2.4"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
  )