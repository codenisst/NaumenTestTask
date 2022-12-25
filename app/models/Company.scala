package models

import play.api.libs.json.{Json, Reads, Writes}

case class Company(inn: Int, name: String) extends Serializable {

}

object Company {
  implicit val reads: Reads[Company] = Json.reads[Company]
  implicit val writes: Writes[Company] = Json.writes[Company]
}