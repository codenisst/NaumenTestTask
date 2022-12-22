package dao

import models.Company

import javax.inject.Singleton

@Singleton
class CompanyDao {

    var company1: Company = new Company(11111111, "ООО Рога и Копыта")
    var company2: Company = new Company(22222222, "ООО Моя оборона")
    var company3: Company = new Company(33333333, "ООО Тоже буква")
    var companyDb: List[Company] = List(company1, company2, company3)

  // create, read, update, delete

  def getAllComp(): List[Company] = companyDb

  def writeComp(newEntity: Company): Unit = companyDb = companyDb.appended(newEntity)

  def getCompByInn(inn: Int): Company = companyDb.filter(x => x.inn == inn).head

  def updateCompByInn(inn: Int, updEntity: Company): Unit = companyDb = companyDb.updated(inn, updEntity)

  def removeCompByInn(inn: Int): Unit = companyDb = companyDb.filter(x => x.inn != inn)
}
