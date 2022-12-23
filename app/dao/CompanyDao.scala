package dao

import models.Company
import models.repositories.CompanyRepo
import org.sqlite.{SQLiteErrorCode, SQLiteException}
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent._
import ExecutionContext.Implicits.global
import javax.inject.Singleton
import scala.concurrent.Await
import scala.concurrent.duration.Duration

@Singleton
class CompanyDao {

  private val db = Database.forURL("jdbc:sqlite:database.sqlite")
  private val companyTable = TableQuery[CompanyRepo]
  db.run(companyTable.schema.create)

  // create, read, update, delete

  def getAllComp(): List[Company] = {
    var resultList: List[Company] = List()
    val resultQuery = db.run(companyTable.sortBy(_.name).result)

    Await.ready(resultQuery.map(vector => {
      resultList = vector.toList
    }), Duration.Inf)

    resultList
  }

  def writeComp(newEntity: Company): Boolean = {
    val query = companyTable += newEntity
    executionAndChecked(query)
  }

  def getCompByInn(inn: Int): List[Company] = {
    var resultList: List[Company] = List()
    val resultQuery = db.run(companyTable.filter(_.inn === inn).result)

    Await.ready(resultQuery.map(vector => {
      resultList = vector.toList
    }), Duration.Inf)

    resultList
  }

  def updateCompByInn(inn: Int, updEntity: Company): Boolean = {
    val query = companyTable.filter(_.inn === inn).map(_.name).update(updEntity.name)
    executionAndChecked(query)
  }


  def removeCompByInn(inn: Int): Boolean = {
    val query = companyTable.filter(_.inn === inn).delete
    executionAndChecked(query)
  }

  def removeAllComp(): Boolean = {
    val query = companyTable.delete
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
