package services

import models.{Company, Employee}

import javax.inject.Singleton

@Singleton
class EmployeeService {

//  var employees: List[String] = List("Anton", "Pavel", "Artem")

  var employee1: Employee = new Employee(0, new Company(55555555, "Company 1"),
    "Anton", "Antonovich", 1000)
  var employee2: Employee = new Employee(1, new Company(11111111, "Company 2"),
    "Oleg", "Olegovich", 2000)
  var employee3: Employee = new Employee(2, new Company(22222222, "Company 3"),
    "Pavel", "Pavlovich", 4000)
  var employeesDb: List[Employee] = List(employee1, employee2, employee3)

  def getAll(): String = {
    var result = ""
    for (x <- employeesDb) result += x.name + " " + x.surname + "\n"
    result
  }

  def create(newEntity: Employee): Unit = employeesDb = employeesDb.appended(newEntity)

  def getById(id: Int): String = employeesDb.filter(x => x.id == id).head.name

  def updateById(id: Int, updEntity: Employee): Unit = employeesDb = employeesDb.updated(id, updEntity)

  def removeById(id: Int): Unit = employeesDb = employeesDb.filter(x => x.id != id)
}
