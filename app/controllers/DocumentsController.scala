package controllers

import domain.Key
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.DocumentService

/**
  * Created by hashcode on 2016/10/12.
  */
class DocumentsController extends Controller {

  def createKey = Action.async(parse.json){
    request =>
      val input = request.body
      val entity = Json.fromJson[Key](input).get
      val results = DocumentService.apply.createKey(entity)
      results.map(result =>
        Ok(Json.toJson(result.toString())))
  }

  def updateKey = Action.async(parse.json){
    request =>
      val input = request.body
      val entity = Json.fromJson[Key](input).get
      val results = DocumentService.apply.updateKey(entity)
      results.map(result =>
        Ok(Json.toJson(result.toString())))
  }

  def getKey = Action.async(parse.json){
    request =>
      val input = request.body
      val entity = Json.fromJson[Key](input).get
      val results = DocumentService.apply.getKey(entity.id)
      results.map(result =>
        Ok(Json.toJson(result)))
  }

  def DeleteKey = Action.async(parse.json){
    request =>
      val input = request.body
      val entity = Json.fromJson[Key](input).get
      val results = DocumentService.apply.deleteKey(entity.id)
      results.map(result =>
        Ok(Json.toJson(result.toString())))
  }
}
