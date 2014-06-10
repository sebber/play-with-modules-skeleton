package controllers

import play.api._
import play.api.mvc._

import play.api.libs.json._

import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Application extends Controller {

  val userRepo = new se.sebber.user.UserRepository()

  def index = Action.async {
    userRepo.all.map { users =>
      Ok(Json.toJson(users))
    }.recover {
      case e =>
        e.printStackTrace()
        BadRequest(e.getMessage())
    }
  }

}