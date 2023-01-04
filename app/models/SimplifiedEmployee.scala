package models

import play.api.libs.json.{Json, Reads, Writes}

case class SimplifiedEmployee(id: Int, companyInn: Long, name: String, surname: String, salary: Int) extends Serializable {

}

object SimplifiedEmployee {
  implicit val reads: Reads[SimplifiedEmployee] = Json.reads[SimplifiedEmployee]
  implicit val writes: Writes[SimplifiedEmployee] = Json.writes[SimplifiedEmployee]
}