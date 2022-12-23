package controllers

import javax.inject.{Inject, Singleton}
import models.Company
import services.CompanyService
import play.api._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

@Singleton
class CompanyController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val companyService: CompanyService = new CompanyService

  def getAll(): Action[AnyContent] =  Action {
    Ok(Json.toJson(companyService.getAll()))
  }

  def create(): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    Ok(companyService.create(Json.fromJson[Company](request.body).get))
  }

  def getByInn(inn: Int): Action[AnyContent] = Action {
    Ok(Json.toJson(companyService.getByInn(inn)))
  }

  def updateByInn(inn: Int): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    Ok(companyService.updateByInn(inn, Json.fromJson[Company](request.body).get))
  }

  def deleteByInn(inn: Int): Action[AnyContent] = Action {
    Ok(companyService.removeByInn(inn))
  }

  def deleteAll(): Action[AnyContent] = Action {
    Ok(companyService.removeAll())
  }
}
