package dao

import models.repositories.{CompanyRepo, SimplifiedEmployeeRepo}
import models.{Company, Employee, SimplifiedEmployee}
import org.sqlite.{SQLiteErrorCode, SQLiteException}
import slick.lifted.TableQuery
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent._
import ExecutionContext.Implicits.global
import javax.inject.Singleton
import scala.concurrent.Await
import scala.concurrent.duration.Duration

@Singleton
class EmployeeDao {

  private val db = Database.forURL("jdbc:sqlite:database.sqlite")
  private val employeeTable = TableQuery[SimplifiedEmployeeRepo]
  private val companyTable = TableQuery[CompanyRepo]
  db.run(employeeTable.schema.create)


  // create, read, update, delete

  def getAllEmp(): List[SimplifiedEmployee] = {
    val resultQuery = db.run(employeeTable.result)
    var resultList: List[SimplifiedEmployee] = List()

    Await.ready(resultQuery.map(vector => {
      resultList = vector.toList
    }), Duration.Inf)

    resultList
  }

  def getAllEmpAndComp(): List[Employee] = {
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

  def writeEmp(simplifiedEmployee: SimplifiedEmployee): Boolean = {
    val query = employeeTable += simplifiedEmployee

    var resultBoolean = true

    try {
      val tmp = Await.result(db.run(query), Duration.Inf)
      if (tmp.toString == "0") throw new SQLiteException("", SQLiteErrorCode.SQLITE_ERROR)
    } catch {
      case e: SQLiteException => resultBoolean = false
    }

    resultBoolean
  }

  def getEmpById(id: Int): List[SimplifiedEmployee] = {
    var resultList: List[SimplifiedEmployee] = List()
    val resultQuery = db.run(employeeTable.filter(_.id === id).result)

    Await.ready(resultQuery.map(vector => {
      resultList = vector.toList
    }), Duration.Inf)

    resultList
  }

  def updateEmpById(id: Int, updEntity: SimplifiedEmployee): Boolean = {
    val query = employeeTable.filter(_.id === id).update(updEntity)
    executionAndChecked(query)
  }

  def removeEmpById(id: Int): Boolean = {
    val query = employeeTable.filter(_.id === id).delete
    executionAndChecked(query)
  }

  def removeAllEmp(): Boolean = {
    val query = employeeTable.delete
    executionAndChecked(query)
  }

  private def executionAndChecked(query: DBIOAction[Int, NoStream, Effect.Write]): Boolean = {
    var resultBoolean = true

    try {
      val tmp = Await.result(db.run(query), Duration.Inf)
      if (tmp.toString == "0") throw new SQLiteException("", SQLiteErrorCode.SQLITE_ERROR)
    } catch {
      case e: SQLiteException => resultBoolean = false
    }

    resultBoolean
  }
}
