package controllers

import models.Company
import services.CompanyService
import play.api._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import javax.inject.{Inject, Singleton}

@Singleton
class CompanyController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val companyService: CompanyService = new CompanyService

  def getAll(): Action[AnyContent] =  Action {
    Ok(Json.toJson(companyService.getAll()))
  }

  def create(): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    companyService.create(Json.fromJson[Company](request.body).get)
    Ok
  }

  def getByInn(inn: Int): Action[AnyContent] = Action {
    Ok(Json.toJson(companyService.getByInn(inn)))
  }

  def updateByInn(inn: Int): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    companyService.updateByInn(inn, Json.fromJson[Company](request.body).get)
    Ok
  }

  def deleteByInn(inn: Int): Action[AnyContent] = Action {
    companyService.removeByInn(inn)
    Ok
  }
}
