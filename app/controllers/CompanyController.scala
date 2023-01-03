package controllers

import models.Company
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.CompanyService

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class CompanyController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with CrudOperations {

  private val companyService: CompanyService = new CompanyService

  override def getAll(): Action[AnyContent] = Action.async {
    val future = companyService.getAll()
    future.map(resultList => {
      if (resultList.nonEmpty) Ok(Json.toJson(resultList)) else NoContent
    })
  }

  override def create(): Action[JsValue] = Action(parse.json).async { implicit request: Request[JsValue] =>
    try {
      val future = companyService.create(Json.fromJson[Company](request.body).get)
      future.map(boolean =>
        if (boolean) Created("Done!")
        else Forbidden("Company already exists"))
    } catch {
      case e: NoSuchElementException => Future {
        BadRequest("Invalid request body")
      }
    }
  }

  override def getByIdentifier(identifier: Int): Action[AnyContent] = Action.async {
    val future = companyService.getByInn(identifier)
    future.map(resultList => {
      if (resultList.nonEmpty) Ok(Json.toJson(resultList.head)) else NoContent
    })
  }

  override def updateByIdentifier(identifier: Int): Action[JsValue] = Action(parse.json).async { implicit request: Request[JsValue] =>
    try {
      val future = companyService.updateByInn(identifier, Json.fromJson[Company](request.body).get)
      future.map(boolean =>
        if (boolean) Accepted("Done!")
        else Forbidden("This company does not exist"))
    } catch {
      case e: NoSuchElementException => Future {
        BadRequest("Invalid request body")
      }
    }
  }

  override def deleteByIdentifier(identifier: Int): Action[AnyContent] = Action.async {
    val future = companyService.removeByInn(identifier)
    future.map(boolean =>
      if (boolean) Accepted("Done!")
      else Forbidden("This company does not exist"))
  }

  override def deleteAll(): Action[AnyContent] = Action.async {
    val future = companyService.removeAll()
    future.map(boolean =>
      if (boolean) Accepted("Done!")
      else Forbidden("Companies not exists"))
  }
}