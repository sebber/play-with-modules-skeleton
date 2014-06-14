package se.sebber.user

import play.api.data._
import play.api.data.Forms._

object DataForm {

  val registerForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "email"    -> email,
      "password" -> nonEmptyText(minLength=8)
    )(User.apply)(User.unapply)
  )

}
