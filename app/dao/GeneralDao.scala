package dao

import models.repositories.{CompanyRepo, SimplifiedEmployeeRepo}
import org.sqlite.{SQLiteErrorCode, SQLiteException}
import slick.jdbc.SQLiteProfile.api._
import slick.lifted.TableQuery

import scala.concurrent._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import ExecutionContext.Implicits.global

trait GeneralDao {

  protected val db = Database.forURL("jdbc:sqlite:database.sqlite")
  protected val companyTable = TableQuery[CompanyRepo]

  //TODO refactor later because Await is used here
  protected def executionAndChecked(query: DBIOAction[Int, NoStream, Effect.Write]): Future[Boolean] = {
    Future {
      var resultBoolean = true

      try {
        val tmp = Await.result(db.run(query), Duration.Inf)
        if (tmp.toString == "0") throw new SQLiteException("", SQLiteErrorCode.SQLITE_ERROR)
      } catch {
        case e: SQLiteException => resultBoolean = false
      }

      resultBoolean
    }
  }
}
