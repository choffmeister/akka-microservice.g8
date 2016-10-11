val akkaVersion = "2.4.9"

lazy val root = Project("$name;format="norm"$", file("."))
  .settings(
    organization := "$organization$",
    version := com.typesafe.sbt.SbtGit.GitKeys.gitDescribedVersion.value.getOrElse("0.0.0-SNAPSHOT"),
    scalaVersion := "2.11.8",
    scalacOptions ++= Seq("-encoding", "utf8", "-deprecation", "-feature")
  )
  .settings(
    resolvers ++= Seq(
      Resolver.jcenterRepo,
      Resolver.bintrayRepo("choffmeister", "maven")
    ),
    libraryDependencies ++= Seq(
      "de.choffmeister" %% "microservice-utils" % "0.0.7",
      "ch.qos.logback" % "logback-classic" % "1.1.7",
      "com.github.dnvriend" %% "akka-persistence-inmemory" % "1.3.6-RC2" % "test",
      "com.github.romix.akka" %% "akka-kryo-serialization" % "0.4.1",
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-core" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion % "test",
      "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.17",
      "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
      "com.typesafe" % "config" % "1.3.0",
      "commons-codec" % "commons-codec" % "1.10",
      "io.spray" %% "spray-json" % "1.3.2",
      "net.logstash.logback" % "logstash-logback-encoder" % "4.7",
      "org.apache.commons" % "commons-email" % "1.4",
      "org.apache.logging.log4j" % "log4j-to-slf4j" % "2.6.2",
      "org.scalatest" %% "scalatest" % "3.0.0" % "test"
    )
  )
  .enablePlugins(DockerBuild.Plugins)
  .settings(DockerBuild.settings)
