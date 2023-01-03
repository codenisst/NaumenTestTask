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
      case e: NoSuchElementException => Future{BadRequest("Invalid request body")}
    }
  }

  override def getByIdentifier(identifier: Int): Action[AnyContent] = Action.async {
    val future = employeesService.getById(identifier)
    future.map(resultList => if (resultList.nonEmpty) Ok(Json.toJson(resultList.head)) else NoContent)
  }

  override def updateByIdentifier(identifier: Int): Action[JsValue] = Action(parse.json).async { implicit request: Request[JsValue] =>
    try {
      val future = employeesService.updateById(identifier, Json.fromJson[Employee](request.body).get)
      future.map(boolean =>
        if (boolean) Accepted("Done!")
        else Forbidden("This employee does not exists!"))
    } catch {
      case e: NoSuchElementException => Future{BadRequest("Invalid request body")}
    }
  }

  override def deleteByIdentifier(identifier: Int): Action[AnyContent] = Action.async {
    val future = employeesService.removeById(identifier)
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
