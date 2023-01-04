package controllers

import play.api.libs.json.Json
import play.api.mvc._

import java.io.FileInputStream
import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class DocController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def getDoc(): Action[AnyContent] = Action.async {
    val stream = new FileInputStream("conf/swagger.json")
    try {
      val json = Json.parse(stream)
      Future {Ok(json)}
    } finally {
      stream.close()
    }
  }
}