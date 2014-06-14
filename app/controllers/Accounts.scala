package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

import se.sebber.user.{ User, UserRegistration }

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.{ Future }


object Accounts extends Controller {

  import se.sebber.user.DataForm.registerForm

  def registration = Action {
    Ok(views.html.accounts.register(registerForm))
  }

  def register = Action.async { implicit request =>
    registerForm.bindFromRequest.fold(
      formWithErrors => {
        Future { 
          BadRequest(views.html.accounts.register(formWithErrors)) 
        }
      },
      user => {        
        UserRegistration(user).execute().map { user =>
          Ok(Json.toJson(user))
        }.recover {
          case e =>
            e.printStackTrace()
            BadRequest(e.getMessage())
        }
      }
    )
    
  }

}