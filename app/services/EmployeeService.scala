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

  def create(newEntity: Employee): Boolean = {
//    val foundedCompany = companyDao.getCompByInn(newEntity.company.inn)
//    if (foundedCompany.isEmpty) companyDao.writeComp(newEntity.company)

    val simplifiedEmployee = SimplifiedEmployee(0, newEntity.company.inn, newEntity.name, newEntity.surname, newEntity.salary)
    employeeDao.writeEmp(simplifiedEmployee)
  }

  def getById(id: Int): List[SimplifiedEmployee] = employeeDao.getEmpById(id)

  def updateById(id: Int, updEntity: Employee): Boolean = {
//    val foundedCompany = companyDao.getCompByInn(updEntity.company.inn)
    val foundedEmployee = employeeDao.getEmpById(id)
    try {
      val simplifiedEmployee = SimplifiedEmployee(foundedEmployee.head.id,
        updEntity.company.inn, updEntity.name, updEntity.surname, updEntity.salary)
      if (employeeDao.updateEmpById(id, simplifiedEmployee)) {
//        if (foundedCompany.isEmpty) companyDao.writeComp(updEntity.company)
        true
      } else {
        false
      }
    } catch {
      case e: NoSuchElementException => false
    }
  }

  def removeById(id: Int): Boolean = {
    if (employeeDao.removeEmpById(id)) true else false
  }

  def removeAll(): Boolean = {
    if (employeeDao.removeAllEmp()) true else false
  }
}
