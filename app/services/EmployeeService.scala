package services

import dao.EmployeeDao
import dao.CompanyDao
import models.{Employee, SimplifiedEmployee}

import javax.inject.Singleton
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class EmployeeService(private val employeeDao: EmployeeDao = new EmployeeDao,
                      private val companyDao: CompanyDao = new CompanyDao) {

  def getAll(): Future[List[SimplifiedEmployee]] = employeeDao.getAllEmp()

  def getAllEmpAndComp(): Future[List[Employee]] = employeeDao.getAllEmpAndComp()

  def create(newEntity: Employee): Future[Boolean] = {
    val _ = companyDao.writeComp(newEntity.company)
    val simplifiedEmployee = SimplifiedEmployee(0, newEntity.company.inn, newEntity.name, newEntity.surname, newEntity.salary)
    employeeDao.writeEmp(simplifiedEmployee)
  }

  def getById(id: Int): Future[List[SimplifiedEmployee]] = employeeDao.getEmpById(id)

  def updateById(id: Int, updEntity: Employee): Future[Boolean] = {
    employeeDao.getEmpById(id).map(list => {
      val foundedSimplifiedEmployee = list.head
      val newSimplifiedEmployee = SimplifiedEmployee(foundedSimplifiedEmployee.id,
        updEntity.company.inn, updEntity.name, updEntity.surname, updEntity.salary)

      val _ = companyDao.writeComp(updEntity.company)
      val _ = employeeDao.updateEmpById(id, newSimplifiedEmployee)
      true
    }).recover {
      case _: NoSuchElementException => false
    }
  }

  def removeById(id: Int): Future[Boolean] = {
    employeeDao.removeEmpById(id)
  }

  def removeAll(): Future[Boolean] = {
    employeeDao.removeAllEmp()
  }
}
