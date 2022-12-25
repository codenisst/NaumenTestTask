package services

import dao.CompanyDao
import models.Company

import javax.inject.Singleton

@Singleton
class CompanyService(private val companyDao: CompanyDao = new CompanyDao) {

  def getAll(): List[Company] = companyDao.getAllComp()

  def create(newEntity: Company): Boolean = {
    if (companyDao.writeComp(newEntity)) true else false
  }

  def getByInn(inn: Int): List[Company] = {
    companyDao.getCompByInn(inn)
  }

  def updateByInn(inn: Int, updEntity: Company): Boolean = {
    if (updEntity.inn == inn) {
      companyDao.updateCompByInn(inn, updEntity)
      true
    } else false
  }

  def removeByInn(inn: Int): Boolean = {
    if (companyDao.removeCompByInn(inn)) true else false
  }

  def removeAll(): Boolean = {
    if (companyDao.removeAllComp()) true else false
  }
}
