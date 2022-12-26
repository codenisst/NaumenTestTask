package controllers

import javax.inject._
import play.api.mvc._
import services.EmployeeService
import models.Employee
import play.api.libs.json.{JsValue, Json}

@Singleton
class EmployeeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with CrudOperations {

  private val employeesService: EmployeeService = new EmployeeService

  override def getAll(): Action[AnyContent] = Action {
    val resultList = employeesService.getAll()
    if (resultList.nonEmpty) Ok(Json.toJson(resultList)) else NoContent
  }

  def getAllEmpAndComp(): Action[AnyContent] = Action {
    val resultList = employeesService.getAllEmpAndComp()
    if (resultList.nonEmpty) Ok(Json.toJson(resultList)) else NoContent
  }

  override def create(): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    try {
      if (employeesService.create(Json.fromJson[Employee](request.body).get)) Created("Done!")
      else Forbidden("Employee already exists")
    } catch {
      case e: NoSuchElementException => BadRequest("Invalid request body")
    }
  }

  override def getByIdentifier(identifier: Int): Action[AnyContent] = Action {
    val resultList = employeesService.getById(identifier)
    if (resultList.nonEmpty) Ok(Json.toJson(resultList.head)) else NoContent
  }

  override def updateByIdentifier(identifier: Int): Action[JsValue] = Action(parse.json) { implicit request: Request[JsValue] =>
    try {
      if (employeesService.updateById(identifier, Json.fromJson[Employee](request.body).get)) Accepted("Done!")
      else Forbidden("This employee does not exists!")
    } catch {
      case e: NoSuchElementException => BadRequest("Invalid request body")
    }
  }

  override def deleteByIdentifier(identifier: Int): Action[AnyContent] = Action {
    if (employeesService.removeById(identifier)) Accepted("Done!") else Forbidden("This employee does not exist")
  }

  override def deleteAll(): Action[AnyContent] = Action {
    if (employeesService.removeAll()) Accepted("Done!") else Forbidden("Employees not exists")
  }
}
