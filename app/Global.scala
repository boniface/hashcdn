import conf.util.CORSFilter
import play.api.libs.json.Json
import play.api.mvc.{Result, RequestHeader, WithFilters}
import play.api.{GlobalSettings, Application}
import play.api.mvc.Results.NotFound

import scala.concurrent.Future

/**
 * Created by hashcode on 2015/10/07.
 */
object Global extends WithFilters(CORSFilter()) with GlobalSettings {
  override def onStart(app: Application): Unit = {
    super.onStart(app)
  }

  override def onHandlerNotFound(request: RequestHeader):Future[Result] = {
    Future.successful(NotFound(Json.obj("error" -> "Wrong Path")))
  }

  // called when a route is found, but it was not possible to bind the request parameters
  override def onBadRequest(request: RequestHeader, error: String):Future[Result] = {
    Future.successful(NotFound(Json.obj("error" -> "Bad Request")))
  }

  // 500 - internal server error
  override def onError(request: RequestHeader, throwable: Throwable) :Future[Result] = {

    Future.successful(NotFound(Json.obj("error" -> "500 Internal Error")))
  }


}