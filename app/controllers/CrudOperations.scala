package controllers

import play.api.libs.json.JsValue
import play.api.mvc._


trait CrudOperations {

  def getAll(): Action[AnyContent]

  def create(): Action[JsValue]

  def getByIdentifier(identifier: Int): Action[AnyContent]

  def updateByIdentifier(identifier: Int): Action[JsValue]

  def deleteByIdentifier(identifier: Int): Action[AnyContent]

  def deleteAll(): Action[AnyContent]
}