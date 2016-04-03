package conf.util

import javax.inject._

import play.api._
import play.api.http.DefaultHttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router

import scala.concurrent._

/**
 * Created by hashcode on 2015/11/07.
 */
class ErrorHandler @Inject()(
         env: Environment,
         config: Configuration,
         sourceMapper: OptionalSourceMapper,
         router: Provider[Router])
  extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  override def onProdServerError(request: RequestHeader, exception: UsefulException) = {
    Future.successful(
      InternalServerError("A server error occurred: " + exception.getMessage)
    )
  }

  override protected def onNotFound(request: RequestHeader, message: String): Future[Result] = {
    Future.successful(
      InternalServerError("A server error occurred: " + message)
    )
  }

  override def onForbidden(request: RequestHeader, message: String) = {
    Future.successful(
      Forbidden("You're not allowed to access this resource.")
    )
  }

  override def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Future.successful(
      Status(statusCode)("A client error occurred: " + message)
    )
  }

  override def onServerError(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      InternalServerError("A server error occurred: " + exception.getMessage)
    )
  }

  override protected def onBadRequest(request: RequestHeader, message: String): Future[Result] = {
    Future.successful(
      InternalServerError("A server error occurred: " + message)
    )
  }




}