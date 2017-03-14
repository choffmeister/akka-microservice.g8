val akkaVersion = "2.4.16"
val akkaHttpVersion = "10.0.3"
val microserviceUtilsVersion = "0.3.1"

lazy val root = Project("$name;format="norm"$", file("."))
  .settings(
    organization := "$organization$",
    version := com.typesafe.sbt.SbtGit.GitKeys.gitDescribedVersion.value.getOrElse("0.0.0-SNAPSHOT"),
    scalaVersion := "2.12.1",
    scalacOptions ++= Seq("-encoding", "utf8", "-deprecation", "-feature")
  )
  .settings(
    resolvers ++= Seq(
      Resolver.jcenterRepo,
      Resolver.bintrayRepo("choffmeister", "maven")
    ),
    libraryDependencies ++= Seq(
      "de.choffmeister" %% "microservice-utils" % microserviceUtilsVersion,
      "ch.qos.logback" % "logback-classic" % "1.1.7",
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % "test",
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
      "com.typesafe" % "config" % "1.3.0",
      "io.spray" %% "spray-json" % "1.3.2",
      "net.logstash.logback" % "logstash-logback-encoder" % "4.7",
      "org.apache.logging.log4j" % "log4j-to-slf4j" % "2.6.2",
      "org.scalatest" %% "scalatest" % "3.0.0" % "test"
    )
  )
  .enablePlugins(DockerBuild.Plugins)
  .settings(DockerBuild.settings)
