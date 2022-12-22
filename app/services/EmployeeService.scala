package services

import dao.EmployeeDao
import models.Employee

import javax.inject.Singleton

@Singleton
class EmployeeService (private val employeeDao: EmployeeDao = new EmployeeDao){

  def getAll(): List[Employee] = employeeDao.getAllEmp()

  def create(newEntity: Employee): Unit = employeeDao.writeEmp(newEntity)

  def getById(id: Int): Employee = employeeDao.getEmpById(id)

  def updateById(id: Int, updEntity: Employee): Unit = employeeDao.updateEmpById(id, updEntity)

  def removeById(id: Int): Unit = employeeDao.removeEmpById(id)
}
