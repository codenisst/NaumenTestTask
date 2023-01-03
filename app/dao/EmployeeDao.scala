package dao

import models.repositories.SimplifiedEmployeeRepo
import models.{Company, Employee, SimplifiedEmployee}
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.TableQuery

import scala.concurrent._
import ExecutionContext.Implicits.global
import javax.inject.Singleton
import scala.concurrent.Await
import scala.concurrent.duration.Duration

@Singleton
class EmployeeDao extends GeneralDao {

  private val employeeTable = TableQuery[SimplifiedEmployeeRepo]

  db.run(employeeTable.schema.create)

  def getAllEmp(): Future[List[SimplifiedEmployee]] = {
    db.run(employeeTable.result).map(vector => vector.toList)
  }

  //TODO refactor later because Await is used here
  def getAllEmpAndComp(): Future[List[Employee]] = {
    Future {
      val query = employeeTable.join(companyTable).on(_.inn === _.inn).sortBy(_._2.name).result
      val resultQuery = Await.result(db.run(query), Duration.Inf)

      var resultList: List[Employee] = List()
      resultQuery.sortBy(_._2.name).foreach(t => {
        val e = t._1
        val c = t._2
        resultList = resultList.::(new Employee(new Company(c.inn, c.name), e.name, e.surname, e.salary))
      })

      resultList.reverse
    }
  }

  def writeEmp(simplifiedEmployee: SimplifiedEmployee): Future[Boolean] = {
    val query = employeeTable += simplifiedEmployee
    executionAndChecked(query)
  }

  def getEmpById(id: Int): Future[List[SimplifiedEmployee]] = {
    db.run(employeeTable.filter(_.id === id).result).map(vector => vector.toList)
  }

  def updateEmpById(id: Int, updEntity: SimplifiedEmployee): Future[Boolean] = {
    val query = employeeTable.filter(_.id === id).update(updEntity)
    executionAndChecked(query)
  }

  def removeEmpById(id: Int): Future[Boolean] = {
    val query = employeeTable.filter(_.id === id).delete
    executionAndChecked(query)
  }

  def removeAllEmp(): Future[Boolean] = {
    val query = employeeTable.delete
    executionAndChecked(query)
  }
}
