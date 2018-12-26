import com.itv.scalapact.plugin._

ThisBuild / version := "0.0.1"
ThisBuild / organization := "com.appliedtype"

lazy val microservice = (project in file("."))
  .enablePlugins(ScalaPactPlugin)
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
    addCommandAlias("testAll", ";reload;test")
  )
  .settings(evictionWarningOptions in update := EvictionWarningOptions.default.withWarnScalaVersionEviction(true))
  .settings(
    resolvers ++= Seq(
      Resolver.jcenterRepo,
      Resolver.typesafeIvyRepo("releases")
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.9"),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.2.4"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
  )