package dao

import models.Company
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent._
import ExecutionContext.Implicits.global
import javax.inject.Singleton

@Singleton
class CompanyDao extends GeneralDao {

  db.run(companyTable.schema.create)

  def getAllComp(): Future[List[Company]] = {
    db.run(companyTable.sortBy(_.name).result).map(vector => vector.toList)
  }

  def writeComp(newEntity: Company): Future[Boolean] = {
    val query = companyTable += newEntity
    executionAndChecked(query)
  }

  def getCompByInn(inn: Long): Future[List[Company]] = {
    db.run(companyTable.filter(_.inn === inn).result).map(vector => vector.toList)
  }

  def updateCompByInn(inn: Long, updEntity: Company): Future[Boolean] = {
    val query = companyTable.filter(_.inn === inn).map(_.name).update(updEntity.name)
    executionAndChecked(query)
  }


  def removeCompByInn(inn: Long): Future[Boolean] = {
    val query = companyTable.filter(_.inn === inn).delete
    executionAndChecked(query)
  }

  def removeAllComp(): Future[Boolean] = {
    val query = companyTable.delete
    executionAndChecked(query)
  }
}
