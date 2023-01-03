package services

import dao.CompanyDao
import models.Company

import javax.inject.Singleton
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class CompanyService(private val companyDao: CompanyDao = new CompanyDao) {

  def getAll(): Future[List[Company]] = companyDao.getAllComp()

  def create(newEntity: Company): Future[Boolean] = {
    companyDao.writeComp(newEntity)
  }

  def getByInn(inn: Int): Future[List[Company]] = {
    companyDao.getCompByInn(inn)
  }

  def updateByInn(inn: Int, updEntity: Company): Future[Boolean] = {
    if (updEntity.inn == inn) {
      companyDao.updateCompByInn(inn, updEntity)
    } else Future {
      false
    }
  }

  def removeByInn(inn: Int): Future[Boolean] = {
    companyDao.removeCompByInn(inn)
  }

  def removeAll(): Future[Boolean] = {
    companyDao.removeAllComp()
  }
}
