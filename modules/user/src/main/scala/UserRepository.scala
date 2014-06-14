package se.sebber.user

import play.api.libs.json._
import play.modules.reactivemongo.json.collection._

import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.{ Future }

class UserRepository {

  val db = se.sebber.db.Env.current

  val usersCollection = db.collection("users")

  def all: Future[List[User]] = {
    val query = Json.obj()
    val cursor = usersCollection.find(query).cursor[User]
    cursor.collect[List]()
  }

  def authenticate(username: String, password: String): Future[Option[User]] = {
    val query = Json.obj("username" -> username, "password" -> password)
    usersCollection.find(query).one[User]
  }

  def insert(user: User): Future[User] = {
    usersCollection.insert(user).map( _ =>
      User("", "", "")
    )
  }

}