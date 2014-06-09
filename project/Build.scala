import sbt._, Keys._
import play.Play.autoImport._
import PlayKeys._
import play._
import play.twirl.sbt.Import._

object Build extends Build {

  import BuildSettings._
  import Dependencies._

  lazy val root = Project("movies", file(".")) enablePlugins PlayScala settings(
    scalaVersion := "2.11.1",
    resolvers ++= Dependencies.Resolvers.commons,
    name := "Movies",
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      cache, ws, RM, PRM
    ),
    scalacOptions := compilerOptions,
    sources in doc in Compile := List()
  ) dependsOn user aggregate user

  lazy val modules = Seq(user, common, db)

  lazy val moduleRefs = modules map projectToRef
  lazy val moduleCPDeps = moduleRefs map { new sbt.ClasspathDependency(_, None) }

  /**
   *  My Modules
   */
  lazy val common = project("common").settings(
    libraryDependencies ++= provided(play.api, play.test, RM)
  )

  lazy val db = project("db", Seq(common)).settings(
    libraryDependencies ++= provided(play.test, play.api, RM, PRM)
  )

  lazy val user = project("user", Seq(common, db)).settings(
    libraryDependencies ++= provided(
      play.api, RM, PRM)
  )

}

