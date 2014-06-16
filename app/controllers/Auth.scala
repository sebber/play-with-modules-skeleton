package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

case class LoginData(username: String, password: String)

object Auth extends BaseController {

  val userRepo = new se.sebber.user.UserRepository()

  val loginForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginData.apply)(LoginData.unapply)
  )

  def login = ContextAction { implicit ctx =>
    Ok(views.html.auth.login(loginForm))
  }

  def authenticate = ContextAction.async {  implicit ctx =>
    loginForm.bindFromRequest.fold(
      formWithErrors => { Future { 
        BadRequest(views.html.auth.login(formWithErrors)) 
      }},
      loginData => { 
        userRepo.authenticate(loginData.username, loginData.password).map { user =>
          import play.api.libs.json._
          Redirect("/")
            .withSession("username" -> loginData.username)
            .flashing("success" -> "You are now signed in")
        }.recover {
          case e =>
            e.printStackTrace()
            BadRequest(views.html.auth.login(loginForm.fill(loginData)))
        }
      }
    )
    
  }

  def logout = AuthenticatedContextAction {
    Ok("Logout").withNewSession
  }

}