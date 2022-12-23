package services

import dao.EmployeeDao
import dao.CompanyDao
import models.{Employee, SimplifiedEmployee}

import javax.inject.Singleton

@Singleton
class EmployeeService(private val employeeDao: EmployeeDao = new EmployeeDao,
                      private val companyDao: CompanyDao = new CompanyDao) {

  def getAll(): List[SimplifiedEmployee] = employeeDao.getAllEmp()

  def getAllEmpAndComp(): List[Employee] = employeeDao.getAllEmpAndComp()

  def create(newEntity: Employee): String = {
    val foundedCompany = companyDao.getCompByInn(newEntity.company.inn)
    if (foundedCompany.isEmpty) companyDao.writeComp(newEntity.company)

    val simplifiedEmployee = SimplifiedEmployee(0, newEntity.company.inn, newEntity.name, newEntity.surname, newEntity.salary)
    employeeDao.writeEmp(simplifiedEmployee)
    "Done!"
  }

  def getById(id: Int): List[SimplifiedEmployee] = employeeDao.getEmpById(id)

  //  def updateById(id: Int, updEntity: Employee): Unit = employeeDao.updateEmpById(id, updEntity)
  def updateById(id: Int, updEntity: Employee): String = {
    val foundedCompany = companyDao.getCompByInn(updEntity.company.inn)
    val foundedEmployee = employeeDao.getEmpById(id)
    try {
      val simplifiedEmployee = SimplifiedEmployee(foundedEmployee.head.id,
        updEntity.company.inn, updEntity.name, updEntity.surname, updEntity.salary)
      if (employeeDao.updateEmpById(id, simplifiedEmployee)) {
        if (foundedCompany.isEmpty) companyDao.writeComp(updEntity.company)
        "Done!"
      } else {
        "This employee does not exists!"
      }
    } catch {
      case e: NoSuchElementException => "This employee does not exists!"
    }
  }


//  def removeById(id: Int): Unit = employeeDao.removeEmpById(id)
  def removeById(id: Int): String = {
    if(employeeDao.removeEmpById(id)) "Done!" else "Failed! No such company exists!"
  }

  def removeAll(): String = {
    if (employeeDao.removeAllEmp()) "Done!" else "Failed! Companies not exists!"
  }
}
