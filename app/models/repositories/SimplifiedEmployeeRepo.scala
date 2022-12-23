package models.repositories

import models.SimplifiedEmployee
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.Tag

class SimplifiedEmployeeRepo(tag: Tag) extends Table[SimplifiedEmployee] (tag, "Employees"){
  private val companyRepo = TableQuery[CompanyRepo]

  def id: Rep[Int] = column[Int]("Id", O.PrimaryKey, O.AutoInc)
  def inn: Rep[Int] = column[Int]("CompanyInnFK")
  def name: Rep[String] = column[String]("Name")
  def surname: Rep[String] = column[String]("Surname")
  def salary: Rep[Int] = column[Int]("Salary")
  def * = (id, inn, name, surname, salary).mapTo[SimplifiedEmployee]

  def company = foreignKey("Company_inn_fk", inn, companyRepo)(_.inn)

}
