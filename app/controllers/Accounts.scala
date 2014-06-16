package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

import se.sebber.user.{ User, UserRegistration }

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.{ Future }


object Accounts extends BaseController {

  import se.sebber.user.DataForm.registerForm

  def registration = ContextAction { implicit ctx =>
    Ok(views.html.accounts.register(registerForm))
  }

  def register = ContextAction.async { implicit ctx =>
    registerForm.bindFromRequest.fold(
      formWithErrors => {
        Future { 
          BadRequest(views.html.accounts.register(formWithErrors)) 
        }
      },
      user => {        
        UserRegistration(user).register().map { user =>
          Redirect("/").flashing(
            "success" -> "You are now a registered user")
        }.recover {
          case e =>
            e.printStackTrace()
            BadRequest(e.getMessage())
        }
      }
    )
    
  }

}