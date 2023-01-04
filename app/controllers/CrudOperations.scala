package controllers

import play.api.libs.json.JsValue
import play.api.mvc._


trait CrudOperations {

  def getAll(): Action[AnyContent]

  def create(): Action[JsValue]

  def getByIdentifier(identifier: Long): Action[AnyContent]

  def updateByIdentifier(identifier: Long): Action[JsValue]

  def deleteByIdentifier(identifier: Long): Action[AnyContent]

  def deleteAll(): Action[AnyContent]
}