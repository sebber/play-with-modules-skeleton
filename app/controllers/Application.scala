package controllers

import play.api._
import play.api.mvc._

import play.api.libs.json._

import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Application extends BaseController {

  val userRepo = new se.sebber.user.UserRepository()

  def index = ContextAction { implicit ctx =>
    Ok(views.html.index())
  }

  def users = ContextAction.async {  implicit ctx =>
    userRepo.all.map { users =>
      Ok(Json.toJson(users)) //views.html.index(users))
    }.recover {
      case e =>
        e.printStackTrace()
        BadRequest(e.getMessage())
    }
  }

}