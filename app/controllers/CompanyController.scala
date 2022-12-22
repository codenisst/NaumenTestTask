package controllers

import models.Company
import services.CompanyService
import play.api._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import javax.inject.{Inject, Singleton}

@Singleton
class CompanyController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  val companyService = new CompanyService

  //complete
  def getAll(): Action[AnyContent] =  Action {
    Ok(companyService.getAll())
  }

  // complete
  def create(): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val newCompany = Json.fromJson[Company](request.body).get
    companyService.create(newCompany)
    Ok
  }

  // complete
  def getByInn(inn: Int): Action[AnyContent] = Action {
    Ok(companyService.getByInn(inn))
  }

  //complete
  def updateByInn(inn: Int): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val updCompany = Json.fromJson[Company](request.body).get
    companyService.updateByInn(inn, updCompany)
    Ok
  }

  //complete
  def deleteByInn(inn: Int): Action[AnyContent] = Action {
    companyService.removeByInn(inn)
    Ok
  }
}
