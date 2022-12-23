package services

import dao.CompanyDao
import models.Company

import javax.inject.Singleton

@Singleton
class CompanyService (private val companyDao: CompanyDao = new CompanyDao){

  def getAll(): List[Company] = companyDao.getAllComp()

  def create(newEntity: Company): String = {
    if (companyDao.writeComp(newEntity)) "Done!" else "Failed! Maybe this company already exist!"
  }

  def getByInn(inn: Int): List[Company] = {
    companyDao.getCompByInn(inn)
  }

  def updateByInn(inn: Int, updEntity: Company): String = {
    if(updEntity.inn == inn) {
      companyDao.updateCompByInn(inn, updEntity)
      "Done!"
    } else {
      "Failed! This company does not exist or the data entered is incorrect"
    }
  }

  def removeByInn(inn: Int): String = {
    if(companyDao.removeCompByInn(inn)) "Done!" else "Failed! No such company exists!"
  }

  def removeAll(): String = {
    if(companyDao.removeAllComp()) "Done!" else "Failed! Companies not exists!"
  }
}
