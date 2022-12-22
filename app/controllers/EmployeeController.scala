package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import services.EmployeeService
import models.Employee
import play.api.libs.json.{JsValue, Json}

@Singleton
class EmployeeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  val employeesService = new EmployeeService

  def getAll(): Action[AnyContent] = Action {
      Ok(employeesService.getAll())
  }

  def create(): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val newEmployee = Json.fromJson[Employee](request.body).get
    employeesService.create(newEmployee)
    Ok
  }

  def getById(id: Int): Action[AnyContent] = Action {
    Ok(employeesService.getById(id))
  }

  def updateById(id: Int): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    val updEmployee = Json.fromJson[Employee](request.body).get
    employeesService.updateById(id, updEmployee)
    Ok
  }

  def deleteById(id: Int): Action[AnyContent] = Action {
    employeesService.removeById(id)
    Ok
  }
}
