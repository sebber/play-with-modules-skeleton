import sbt._, Keys._
import play.Play.autoImport._
import PlayKeys._
import play._
import play.twirl.sbt.Import._

object Build extends Build {

  import BuildSettings._

  lazy val root = Project(id = "root", base = file(".")) enablePlugins PlayScala settings(
    scalaVersion := "2.11.1",
    name := "moduled-app",
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      jdbc, anorm, cache, ws
    )
  ) dependsOn user aggregate user

  lazy val modules = Seq(user)

  lazy val moduleRefs = modules map projectToRef
  lazy val moduleCPDeps = moduleRefs map { new sbt.ClasspathDependency(_, None) }

  lazy val user = project("user")

}

