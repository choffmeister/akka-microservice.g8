import com.typesafe.sbt.packager.Keys._
import com.typesafe.sbt.packager.archetypes._
import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport.Docker
import com.typesafe.sbt.packager.docker.{Cmd, DockerPlugin, ExecCmd}
import sbt.Keys._
import sbt._

object DockerBuild {
  val Plugins = JavaServerAppPackaging && DockerPlugin && AshScriptPlugin

  def settings = Seq(
    dockerRepository := Some(name.value),
    packageName in Docker := name.value,
    version in Docker := "latest",

    dockerBaseImage := "openjdk:8u111-jre-alpine",
    dockerExposedPorts := 8080 :: Nil,

    defaultLinuxInstallLocation in Docker := s"/opt/\${(packageName in Docker).value}",
    daemonUser in Docker := "root",
    daemonGroup in Docker := "root",

    dockerCommands := {
      dockerCommands.value.take(2) ++
        Seq(
          Cmd("RUN apk add --no-cache --update curl"),
          Cmd("HEALTHCHECK --interval=5s --timeout=1s --retries=3 CMD curl --fail localhost:8080/_health || exit 1")) ++
        dockerCommands.value.drop(2)
    },

    bashScriptExtraDefines ++= Seq(
      """opts="\$opts -Dapp.home=\${app_home}/.."""",
      """opts="\$opts -Dconfig.file=\${app_home}/../conf/application.conf"""",
      """opts="\$opts -Dlogback.configurationFile=\${app_home}/../conf/logback.xml"""",
      """opts="\$opts \${JAVA_OPTS}""""
    ),
    makeBatScript := None
  )
}
