package services

import dao.EmployeeDao
import dao.CompanyDao
import models.{Employee, SimplifiedEmployee}

import javax.inject.Singleton
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

@Singleton
class EmployeeService(private val employeeDao: EmployeeDao = new EmployeeDao,
                      private val companyDao: CompanyDao = new CompanyDao) {

  def getAll(): Future[List[SimplifiedEmployee]] = employeeDao.getAllEmp()

  def getAllEmpAndComp(): Future[List[Employee]] = employeeDao.getAllEmpAndComp()

  // TODO refactor later because Await is used here
  def create(newEntity: Employee): Future[Boolean] = {
    val foundedCompany = Await.result(companyDao.getCompByInn(newEntity.company.inn), Duration.Inf)
    if (foundedCompany.isEmpty) Await.ready(companyDao.writeComp(newEntity.company), Duration.Inf)

    val simplifiedEmployee = SimplifiedEmployee(0, newEntity.company.inn, newEntity.name, newEntity.surname, newEntity.salary)
    employeeDao.writeEmp(simplifiedEmployee)
  }

  def getById(id: Int): Future[List[SimplifiedEmployee]] = employeeDao.getEmpById(id)

  // TODO refactor later because Await is used here
  def updateById(id: Int, updEntity: Employee): Future[Boolean] = {
    Future {
      val foundedCompany = Await.result(companyDao.getCompByInn(updEntity.company.inn), Duration.Inf)
      val foundedEmployee = Await.result(employeeDao.getEmpById(id), Duration.Inf)
      try {
        val simplifiedEmployee = SimplifiedEmployee(foundedEmployee.head.id,
          updEntity.company.inn, updEntity.name, updEntity.surname, updEntity.salary)
        if (Await.result(employeeDao.updateEmpById(id, simplifiedEmployee), Duration.Inf)) {
          if (foundedCompany.isEmpty) companyDao.writeComp(updEntity.company)
          true
        } else {
          false
        }
      } catch {
        case e: NoSuchElementException => false
      }
    }
  }

  def removeById(id: Int): Future[Boolean] = {
    employeeDao.removeEmpById(id)
  }

  def removeAll(): Future[Boolean] = {
    employeeDao.removeAllEmp()
  }
}
