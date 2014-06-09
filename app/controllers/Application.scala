package controllers

import play.api._
import play.api.mvc._

import se.sebber.user.User

object Application extends Controller {

  def index = Action {
    val user = User("Bass")
    Ok(user.name)
  }

}