package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

case class LoginData(username: String, password: String)

object Auth extends Controller {

  val userRepo = new se.sebber.user.UserRepository()

  val loginForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginData.apply)(LoginData.unapply)
  )

  def login = Action {
    Ok(views.html.auth.login(loginForm))
  }

  def authenticate = Action.async { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => { Future { 
        BadRequest(views.html.auth.login(formWithErrors)) 
      }},
      loginData => { 
        userRepo.authenticate(loginData.username, loginData.password).map { user =>
          import play.api.libs.json._
          Ok(Json.toJson(user))
        }.recover {
          case e =>
            e.printStackTrace()
            BadRequest(views.html.auth.login(loginForm.fill(loginData)))
        }
      }
    )
    
  }

}