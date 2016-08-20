import com.typesafe.sbt.packager.Keys._
import com.typesafe.sbt.packager.archetypes._
import com.typesafe.sbt.packager.docker.DockerPlugin.autoImport.Docker
import com.typesafe.sbt.packager.docker.{Cmd, DockerPlugin, ExecCmd}
import sbt.Keys._
import sbt._

object DockerBuild {
  val Plugins = JavaServerAppPackaging && DockerPlugin

  def settings = Seq(
    dockerRepository := Some(name.value),
    packageName in Docker := name.value,
    version in Docker := "latest",

    dockerBaseImage := "java:8u66-jre",
    dockerExposedPorts := 8080 :: 2551 :: Nil,

    defaultLinuxInstallLocation in Docker := s"/opt/\${(packageName in Docker).value}",
    daemonUser in Docker := "root",
    daemonGroup in Docker := "root",

    dockerCommands <<= dockerCommands map { cmds =>
      cmds.filterNot {
        case Cmd("USER", args @ _*) => true
        case ExecCmd("RUN", args @ _*) => args.contains("chown")
        case cmd => false
      }
    },

    bashScriptExtraDefines ++= Seq(
      """addJava "-Dapp.home=\${app_home}/.."""",
      """addJava "-Dconfig.file=\${app_home}/../conf/application.conf"""",
      """addJava "-Dlogback.configurationFile=\${app_home}/../conf/logback.xml""""
    ),
    makeBatScript := None
  )
}
