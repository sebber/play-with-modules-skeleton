package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import play.api.libs.json._

import se.sebber.user.User

case class RegisterData(username: String, email: String, password: String)

object Accounts extends Controller {

  val registerForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "email"    -> email,
      "password" -> nonEmptyText(minLength=8)
    )(RegisterData.apply)(RegisterData.unapply)
  )

  def registration = Action {
    Ok(views.html.accounts.register(registerForm))
  }

  def register = Action { implicit request =>
    registerForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.accounts.register(formWithErrors)) 
      },
      registerData => {         
        Ok(Json.toJson(User(registerData.username, registerData.email, registerData.password)))
      }
    )
    
  }

}