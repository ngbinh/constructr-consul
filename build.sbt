lazy val constructrRoot = project
  .copy(id = "constructr-root")
  .in(file("."))
  .enablePlugins(GitVersioning)
  .aggregate(constructrCoordinationConsul, constructrCoordinationDemo, constructrCoordinationTesting)

lazy val constructrCoordinationConsul = project
  .copy(id = "constructr-coordination-consul")
  .in(file("constructr-coordination-consul"))
  .enablePlugins(AutomateHeaderPlugin)

lazy val constructrCoordinationTesting = project
  .copy(id = "constructr-coordination-testing")
  .in(file("constructr-coordination-testing"))
  .configs(MultiJvm)
  .dependsOn(constructrCoordinationConsul % "test->compile")

lazy val constructrCoordinationDemo = project
  .copy(id = "constructr-coordination-demo")
  .in(file("constructr-coordination-demo"))
  .enablePlugins(AutomateHeaderPlugin, AshScriptPlugin)
  .dependsOn(constructrCoordinationConsul)
  .settings(dockerSettings)

lazy val dockerSettings: Seq[Setting[_]] = Seq(
  maintainer in Docker := "Tecsisa",
  dockerBaseImage := "frolvlad/alpine-oraclejdk8",
  daemonUser in Docker := "root",
  version in Docker := "latest"
)

name := "constructr-root"

publishArtifact := false