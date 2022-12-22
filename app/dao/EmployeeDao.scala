package dao

import models.{Company, Employee}

import javax.inject.Singleton

@Singleton
class EmployeeDao {

  var employee1: Employee = new Employee(0, new Company(55555555, "Company 1"),
    "Anton", "Antonovich", 1000)
  var employee2: Employee = new Employee(1, new Company(11111111, "Company 2"),
    "Oleg", "Olegovich", 2000)
  var employee3: Employee = new Employee(2, new Company(22222222, "Company 3"),
    "Pavel", "Pavlovich", 4000)
  var employeesDb: List[Employee] = List(employee1, employee2, employee3)

  // create, read, update, delete

  def getAllEmp(): List[Employee] = employeesDb

  def writeEmp(newEntity: Employee): Unit = employeesDb = employeesDb.appended(newEntity)

  def getEmpById(id: Int): Employee = employeesDb.filter(x => x.id == id).head

  def updateEmpById(id: Int, updEntity: Employee): Unit = employeesDb = employeesDb.updated(id, updEntity)

  def removeEmpById(id: Int): Unit = employeesDb = employeesDb.filter(x => x.id != id)

}
