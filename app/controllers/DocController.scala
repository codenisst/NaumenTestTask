package controllers

import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import java.io.FileInputStream
import javax.inject.{Inject, Singleton}

@Singleton
class DocController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def getDoc(): Action[AnyContent] = Action {
    val stream = new FileInputStream("conf/swagger.json")
    try{
      val json = Json.parse(stream)
      Ok(json)
    } finally {
      stream.close()
    }
  }
}