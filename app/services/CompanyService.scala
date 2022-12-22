package services

import dao.CompanyDao
import models.Company

import javax.inject.Singleton

@Singleton
class CompanyService (private val companyDao: CompanyDao = new CompanyDao){

  def getAll(): List[Company] = companyDao.getAllComp()

  def create(newEntity: Company): Unit = companyDao.writeComp(newEntity)

  def getByInn(inn: Int): Company = companyDao.getCompByInn(inn)

  def updateByInn(inn: Int, updEntity: Company): Unit = companyDao.updateCompByInn(inn, updEntity)

  def removeByInn(inn: Int): Unit = companyDao.removeCompByInn(inn)
}
