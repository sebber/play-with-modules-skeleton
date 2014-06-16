package controllers

import play.api._
import play.api.mvc._

//import entities.ContextRequest

private[controllers] trait BaseController extends Controller {

  import Results._
  import scala.concurrent.Future

  object AuthenticatedContextAction extends ActionBuilder[ContextRequest] {
    def invokeBlock[A](request: Request[A], block: (ContextRequest[A]) => Future[Result]) = {
      val cRequest = new ContextRequest(request)
      cRequest.isAuthenticated match {
        case true => block(cRequest)
        case false => Future.successful(Forbidden)
      }
    }
  }

  object NotAuthenticatedContextAction extends ActionBuilder[ContextRequest] {
    def invokeBlock[A](request: Request[A], block: (ContextRequest[A]) => Future[Result]) = {
      val cRequest = new ContextRequest(request)
      cRequest.isAuthenticated match {
        case true => Future.successful(Forbidden)
        case false => block(cRequest)
      }
    }
  }

  object ContextAction extends ActionBuilder[ContextRequest] {
    def invokeBlock[A](request: Request[A], block: (ContextRequest[A]) => Future[Result]) = {
      Logger.debug("Context loaded")
      block(new ContextRequest(request))
    }
  }

}

class ContextRequest[A](request: Request[A]) extends WrappedRequest[A](request) {

  var isAuthenticated = session.get("username").isDefined

  /*lazy val user: User = session.get("username").map 
    { username => User(username) } getOrElse { User("Anonymous") }

  lazy val isAuthenticated = user.username != "Anonymous"
  */

}