import sbt._, Keys._
import play.Play.autoImport._

object BuildSettings {

  def buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "se.sebber",
    scalaVersion := "2.11.1",
    sources in doc in Compile := List())

  def defaultDeps = Seq.empty

  val srcMain = Seq(
    scalaSource in Compile <<= (sourceDirectory in Compile)(identity),
    scalaSource in Test <<= (sourceDirectory in Test)(identity)
  )

  def project(name: String, deps: Seq[sbt.ClasspathDep[sbt.ProjectReference]] = Seq.empty) = 
  Project(
    name,
    file("modules/" + name),
    dependencies = deps,
    settings = Seq(
      version := "1.0-SNAPSHOT",
      libraryDependencies := defaultDeps
    ) ++ buildSettings ++ srcMain
  )

  def projectToRef(p: Project): ProjectReference = LocalProject(p.id)

}