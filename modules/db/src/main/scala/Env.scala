package se.sebber.db

import com.typesafe.config.Config
import play.modules.reactivemongo.ReactiveMongoPlugin
import reactivemongo.api._
//import Types._

final class Env() {

  lazy val db = {
    import play.api.Play.current
    ReactiveMongoPlugin.db
  }

  def apply(name: String): Collection = db(name)

}

object Env {

  //lazy val current = "[boot] db" describes new Env(
  //  lila.common.PlayApp loadConfig "mongodb")

  lazy val current = new Env()
}