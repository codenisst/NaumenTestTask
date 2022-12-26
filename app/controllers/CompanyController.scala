package controllers

import models.Company

import javax.inject.{Inject, Singleton}
import services.CompanyService
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

@Singleton
class CompanyController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with CrudOperations {

  private val companyService: CompanyService = new CompanyService

  override def getAll(): Action[AnyContent] = Action {
    val resultList = companyService.getAll()
    if (resultList.nonEmpty) Ok(Json.toJson(resultList)) else NoContent
  }

  override def create(): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    try {
      if (companyService.create(Json.fromJson[Company](request.body).get)) Created("Done!")
      else Forbidden("Company already exists")
    } catch {
      case e: NoSuchElementException => BadRequest("Invalid request body")
    }
  }

  override def getByIdentifier(identifier: Int): Action[AnyContent] = Action {
    val resultList = companyService.getByInn(identifier)
    if (resultList.nonEmpty) Ok(Json.toJson(resultList.head)) else NoContent
  }

  override def updateByIdentifier(identifier: Int): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    try {
      if (companyService.updateByInn(identifier, Json.fromJson[Company](request.body).get)) Accepted("Done!")
      else Forbidden("This company does not exist")
    } catch {
      case e: NoSuchElementException => BadRequest("Invalid request body")
    }
  }

  override def deleteByIdentifier(identifier: Int): Action[AnyContent] = Action {
    if (companyService.removeByInn(identifier)) Accepted("Done!") else Forbidden("This company does not exist")
  }

  override def deleteAll(): Action[AnyContent] = Action {
    if (companyService.removeAll()) Accepted("Done!") else Forbidden("Companies not exists")
  }
}