import com.typesafe.sbt.packager.archetypes.ServerLoader

name := """hashcdn"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala,DebianPlugin,JavaServerAppPackaging)

scalaVersion := "2.11.8"

maintainer := "Boniface Kabaso <boniface@kabaso.com>"
packageSummary in Linux := "Hashcode CDN"
packageDescription :=  "Hashcode CDN API Backend "
serverLoading in Debian := ServerLoader.SystemV

bashScriptExtraDefines ++= Seq(
  """addJava "-Xms4096m"""",
  """addJava "-Xmx4096m""""
)


libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
"org.scalatest" % "scalatest_2.11"                 % "2.2.6"
)

libraryDependencies += "org.mongodb" % "casbah-gridfs_2.11" % "3.1.1"

libraryDependencies += "junit" % "junit" % "4.12"

libraryDependencies += "org.mongodb" % "casbah_2.11" % "3.1.1"

libraryDependencies += "org.apache.tika" % "tika-core" % "1.12"

libraryDependencies += "org.apache.tika" % "tika" % "1.12"

libraryDependencies += "org.imgscalr" % "imgscalr-lib" % "4.2"

libraryDependencies += "com.sksamuel.scrimage" % "scrimage-core_2.11" % "2.1.5"

libraryDependencies += filters

resolvers ++= Seq(
  "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype repo"                    at "https://oss.sonatype.org/content/groups/scala-tools/",
  "Sonatype releases"                at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype snapshots"               at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype staging"                 at "http://oss.sonatype.org/content/repositories/staging",
  "Java.net Maven2 Repository"       at "http://download.java.net/maven/2/",
  "Twitter Repository"               at "http://maven.twttr.com",
  "Websudos releases"                at "https://dl.bintray.com/websudos/oss-releases/"
)


resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
