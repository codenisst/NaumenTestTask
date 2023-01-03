package dao

import models.repositories.CompanyRepo
import org.sqlite.SQLiteException
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.TableQuery

import scala.concurrent._
import ExecutionContext.Implicits.global

trait GeneralDao {

  protected val db = Database.forURL("jdbc:sqlite:database.sqlite")
  protected val companyTable = TableQuery[CompanyRepo]

  protected def executionAndChecked(query: DBIOAction[Int, NoStream, Effect.Write]): Future[Boolean] = {
    db.run(query).map(value =>
      if (value != 0) true
      else false).recover {
      case _: SQLiteException => false
    }
  }
}
