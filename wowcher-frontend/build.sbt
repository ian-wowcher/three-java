import com.atlassian.labs.gitstamp.GitStampPlugin._

name := """wowcher-frontend"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.2"

resolvers += Resolver.mavenLocal

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += ws

libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.2.10"

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.1.1"

libraryDependencies += "org.scalatestplus" %% "play" % "1.2.0" % "test"

libraryDependencies += "com.github.pathikrit" %% "dijon" % "0.2.4"

libraryDependencies += "com.mohiva" %% "play-html-compressor" % "0.3"

lazy val WithBackend = config("withbackend") extend(Test)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings()
  .configs(WithBackend)
  .settings(inConfig(WithBackend)(Defaults.testTasks): _*)
  .settings(testOptions in WithBackend := Seq(Tests.Argument()))
  .settings(testOptions in Test := Seq(Tests.Argument("-l", "wowcher.RequiresBackend")))

TwirlKeys.templateImports += "WowcherViewImports._"

Seq(gitStampSettings: _*)