import play.twirl.sbt.Import.TwirlKeys
import play.twirl.sbt.SbtTwirl
import sbt.Build

import sbt._
import sbt.Keys._

object HmrcBuild extends Build {

  import uk.gov.hmrc.PublishingSettings._
  import uk.gov.hmrc.NexusPublishing._
  import uk.gov.hmrc.DefaultBuildSettings
  import scala.util.Properties.envOrElse
  import DefaultBuildSettings._
  import uk.gov.hmrc.{SbtBuildInfo, ShellPrompt}
  import Dependencies._

  val nameApp = "play-ui"
  val versionApp = envOrElse("PLAY_UI_VERSION", "999-SNAPSHOT")

  val appDependencies = Seq(
    Compile.play,
    Compile.playFilters,

    Test.scalaTest,
    Test.pegdown,
    Test.jsoup,
    Test.playTest
  )

  lazy val playUi = (project in file("."))
    .settings(name := nameApp)
    .settings(version := versionApp)
    .settings(scalaSettings : _*)
    .settings(defaultSettings() : _*)
    .settings(
      targetJvm := "jvm-1.7",
      shellPrompt := ShellPrompt(versionApp),
      libraryDependencies ++= appDependencies,
      crossScalaVersions := Seq("2.11.4", "2.10.4")
    )
    .settings(publishAllArtefacts: _*)
    .settings(nexusPublishingSettings: _*)
    .settings(SbtBuildInfo(): _*)
    .enablePlugins(SbtTwirl)
    .settings(TwirlKeys.templateImports ++= Seq("play.api.mvc._", "play.api.data._", "play.api.i18n._", "play.api.templates.PlayMagic._"))
    .settings(unmanagedSourceDirectories in sbt.Compile += baseDirectory.value / "src/main/twirl")

}

object Dependencies {

  object Compile {
    val play = "com.typesafe.play" %% "play" % "2.3.4" % "provided"
    val playFilters = "com.typesafe.play" %% "filters-helpers" % "2.3.4" % "provided"
  }

  sealed abstract class Test(scope: String) {
    val scalaTest = "org.scalatest" %% "scalatest" % "2.2.1" % scope
    val pegdown = "org.pegdown" % "pegdown" % "1.4.2" % scope
    val jsoup = "org.jsoup" % "jsoup" % "1.7.2" % scope
    val playTest = "com.typesafe.play" %% "play-test" % "2.3.4" % scope
  }

  object Test extends Test("test")

  object IntegrationTest extends Test("it")

}

