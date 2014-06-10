package controllers

import play.api._
import play.api.mvc._

import se.sebber.user.User

import play.api.libs.json._
import play.api.libs.functional.syntax._

import play.modules.reactivemongo.json.BSONFormats.toJSON
import play.modules.reactivemongo.json.ImplicitBSONHandlers.JsObjectWriter

import reactivemongo.api.gridfs.Implicits.DefaultReadFileReader
import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.api._
import reactivemongo.bson._

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.{ Future }

object Application extends Controller {

  val db = se.sebber.db.Env.current

  val usersCollection = db.collection("user")

  def index = Action.async {
    findUsers.map { users =>
      Ok(Json.toJson(users))
    }.recover {
      case e =>
        e.printStackTrace()
        BadRequest(e.getMessage())
    }
  }



  def findUsers: Future[List[User]] = {
    val query = BSONDocument()

    val cursor = usersCollection.find(query).cursor[User]

    cursor.collect[List]()
  }

}