package se.sebber.user

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._

import reactivemongo.bson._

case class User(
  username: String,
  email: String
)

object User {

  implicit object UserBSONReader extends BSONDocumentReader[User] {
    def read(doc: BSONDocument): User = User(
      doc.getAs[String]("username").get,
      doc.getAs[String]("email").get
    )
  }

  implicit object UserBSONWriter extends BSONDocumentWriter[User] {
    def write(user: User): BSONDocument = BSONDocument(
      "username" -> user.username,
      "email" -> user.email
    )
  }

  implicit val userWrites: Writes[User] = (
    (JsPath \ "username").write[String] and
    (JsPath \ "email").write[String]
  )(unlift(User.unapply))

}