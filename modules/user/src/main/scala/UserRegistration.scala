package se.sebber.user

import scala.concurrent.{ Future }
import play.api.libs.concurrent.Execution.Implicits.defaultContext

case class UserRegistration(user: User) {
  
  val userRepo = new UserRepository

  def register(): Future[User] = {
    userRepo.insert(user)

    // Send mailconfirmation
  }

}