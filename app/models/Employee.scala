package models

import play.api.libs.json.{Json, Reads, Writes}

case class Employee (id: Int, var company: Company, name: String, surname: String, var salary: Int) {

}

object Employee {
  implicit val reads: Reads[Employee] = Json.reads[Employee]
  implicit val writes: Writes[Employee] = Json.writes[Employee]
}