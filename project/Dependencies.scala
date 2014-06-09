import sbt._, Keys._

object Dependencies {

  private val home = "file://" + Path.userHome.absolutePath

  object Resolvers {
    val typesafe = "typesafe.com releases" at "http://repo.typesafe.com/typesafe/releases/"
    val typesafeS = "typesafe.com snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"
    val sonatype = "sonatype" at "http://oss.sonatype.org/content/repositories/releases"
    val sonatypeS = "sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots"
  

    val commons = Seq(
      typesafe, typesafeS, sonatype, sonatypeS
    )
  }

  val config = "com.typesafe" % "config" % "1.2.1"
  val RM = "org.reactivemongo" %% "reactivemongo" % "0.11.0-SNAPSHOT"
  val PRM = "org.reactivemongo" %% "play2-reactivemongo" % "0.11.0-SNAPSHOT"

  object play {
    val version = "2.3.0"
    val api = "com.typesafe.play" %% "play" % version
    val test = "com.typesafe.play" %% "play-test" % version
  }
}
