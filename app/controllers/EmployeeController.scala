package controllers

import javax.inject._
import play.api.mvc._
import services.EmployeeService
import models.Employee
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class EmployeeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController with CrudOperations {

  private val employeesService: EmployeeService = new EmployeeService

  override def getAll(): Action[AnyContent] = Action.async {
    val future = employeesService.getAll()
    future.map(resultList => if (resultList.nonEmpty) Ok(Json.toJson(resultList)) else NoContent)
  }

  def getAllEmpAndComp(): Action[AnyContent] = Action.async {
    val future = employeesService.getAllEmpAndComp()
    future.map(resultList => if (resultList.nonEmpty) Ok(Json.toJson(resultList)) else NoContent)
  }

  override def create(): Action[JsValue] = Action(parse.json).async { implicit request: Request[JsValue] =>
    try {
      val future = employeesService.create(Json.fromJson[Employee](request.body).get)
      future.map(boolean =>
      if (boolean) Created("Done!")
      else Forbidden("Employee already exists"))
    } catch {
      case _: NoSuchElementException => Future{BadRequest("Invalid request body")}
    }
  }

  override def getByIdentifier(identifier: Long): Action[AnyContent] = Action.async {
    val future = employeesService.getById(identifier.toInt)
    future.map(resultList => if (resultList.nonEmpty) Ok(Json.toJson(resultList.head)) else NoContent)
  }

  override def updateByIdentifier(identifier: Long): Action[JsValue] = Action(parse.json).async { implicit request: Request[JsValue] =>
    try {
      val future = employeesService.updateById(identifier.toInt, Json.fromJson[Employee](request.body).get)
      future.map(boolean =>
        if (boolean) Accepted("Done!")
        else Forbidden("This employee does not exists!"))
    } catch {
      case _: NoSuchElementException => Future{BadRequest("Invalid request body")}
    }
  }

  override def deleteByIdentifier(identifier: Long): Action[AnyContent] = Action.async {
    val future = employeesService.removeById(identifier.toInt)
    future.map(boolean =>
      if (boolean) Accepted("Done!")
      else Forbidden("This employee does not exist"))
  }

  override def deleteAll(): Action[AnyContent] = Action.async {
    val future = employeesService.removeAll()
    future.map(boolean =>
      if (boolean) Accepted("Done!")
      else Forbidden("Employees not exists"))
  }
}
