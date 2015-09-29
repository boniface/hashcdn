name := """hashcdn"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

val PhantomVersion = "1.12.2"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
"com.websudos"  %% "phantom-dsl"                   % PhantomVersion,
"com.websudos"  %% "phantom-testkit"               % PhantomVersion,
"org.scalatest" % "scalatest_2.11"                 % "2.2.5"
)

libraryDependencies += "org.mongodb" % "casbah-gridfs_2.11" % "2.8.2"

libraryDependencies += "junit" % "junit" % "4.12"

libraryDependencies += "org.mongodb" % "casbah_2.11" % "2.8.2"

libraryDependencies += "org.apache.tika" % "tika-core" % "1.10"

libraryDependencies += "org.apache.tika" % "tika" % "1.10"

libraryDependencies += "org.imgscalr" % "imgscalr-lib" % "4.2"

libraryDependencies += "com.sksamuel.scrimage" % "scrimage-core_2.11" % "2.1.1"


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
