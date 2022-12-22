package services

import models.Company

import javax.inject.Singleton

@Singleton
class CompanyService {

  var company1: Company = new Company(11111111, "ООО Рога и Копыта")
  var company2: Company = new Company(22222222, "ООО Моя оборона")
  var company3: Company = new Company(33333333, "ООО Тоже буква")
  var companyDb: List[Company] = List(company1, company2, company3)

  def getAll(): String = {
    var result = ""
    for (x <- companyDb) result += x.inn.toString + " " + x.name + "\n"
    result
  }

  def create(newEntity: Company): Unit = companyDb = companyDb.appended(newEntity)

  def getByInn(inn: Int): String = companyDb.filter(x => x.inn == inn).head.name

  def updateByInn(inn: Int, updEntity: Company): Unit = companyDb = companyDb.updated(inn, updEntity)

  def removeByInn(inn: Int): Unit = companyDb = companyDb.filter(x => x.inn != inn)
}
