package conf.security

import play.api.libs.Files.TemporaryFile
import play.api.libs.json.JsValue
import play.api.mvc.{AnyContent, MultipartFormData, Request}
import services.DocumentService

/**
  * Created by hashcode on 2016/10/12.
  */

case class LogInStatus(status: String)

object TokenCheck {
  def getTokenfromRequest(request: Request[MultipartFormData[TemporaryFile]]) = {
    val token = request.headers.get("Authorization")
    val tokenValue = token match {
      case Some(t) => t
      case None => ""
    }

    check(tokenValue) map (tkn => {
      if (tkn) LogInStatus("VALID")
      else LogInStatus("INVALID")
    })

  }

  def getTokenfromParam(request: Request[AnyContent]) = {
    val token = request.headers.get("Authorization")
    val tokenValue = token match {
      case Some(t) => t
      case None => ""
    }

    check(tokenValue) map (tkn => {
      if (tkn) LogInStatus("VALID")
      else LogInStatus("INVALID")
    })
  }

  def getToken(request: Request[JsValue]) = {
    val token = request.headers.get("Authorization")
    val tokenValue = token match {
      case Some(t) => t
      case None => ""
    }
    check(tokenValue) map (tkn => {
      if (tkn) LogInStatus("VALID")
      else LogInStatus("INVALID")
    })
  }

  private def check(token: String) = {
    val documentKey = DocumentService.apply.getKey("APIKEY")
    documentKey map (key => key.value == token)
  }

}
