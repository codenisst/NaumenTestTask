package models.repositories

import models.Company
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.Tag

class CompanyRepo(tag: Tag) extends Table[Company](tag, "Companies") {
  def inn: Rep[Long] = column[Long]("Inn", O.PrimaryKey)

  def name: Rep[String] = column[String]("Name")

  def * = (inn, name).mapTo[Company]
}

