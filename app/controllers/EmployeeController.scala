package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import services.EmployeeService
import models.Employee
import play.api.libs.json.{JsValue, Json}

@Singleton
class EmployeeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val employeesService: EmployeeService = new EmployeeService

  def getAll(): Action[AnyContent] = Action {
    Ok(Json.toJson(employeesService.getAll()))
  }

  def getAllEmpAndComp(): Action[AnyContent] = Action {
    Ok(Json.toJson(employeesService.getAllEmpAndComp()))
  }

  def create(): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    Ok(employeesService.create(Json.fromJson[Employee](request.body).get))
  }

  def getById(id: Int): Action[AnyContent] = Action {
    Ok(Json.toJson(employeesService.getById(id)))
  }

  def updateById(id: Int): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    Ok(employeesService.updateById(id, Json.fromJson[Employee](request.body).get))
  }

  def deleteById(id: Int): Action[AnyContent] = Action {
    Ok(employeesService.removeById(id))
  }

  def deleteAll(): Action[AnyContent] = Action {
    Ok(employeesService.removeAll())
  }
}
