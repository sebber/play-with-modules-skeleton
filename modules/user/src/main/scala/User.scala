package se.sebber.user

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._

//import reactivemongo.bson._
import play.modules.reactivemongo.json.collection._

case class User(
  username: String,
  email: String,
  password: String
)

object User {

  implicit val userWriter: Writes[User] = Json.writes[User]
  implicit val userReader: Reads[User] = Json.reads[User]

}