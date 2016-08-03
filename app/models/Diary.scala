package models

import java.sql.Timestamp
import java.text.SimpleDateFormat
import javax.inject.Inject

import play.api.Logger
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

/**
  *
  * @author kazamai
  * @version 1.0 8/3/16.
  */
case class Diary(id: Long,
                 contents: String,
                 diaryDate: Option[Timestamp])

class DiaryRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db
  import dbConfig.driver.api._
  private val Diaries = TableQuery[DiariesTable]

  def createTable(): Unit = db.run(Diaries.schema.create)

  def all: Future[List[Diary]] =
    db.run(Diaries.sortBy(_.diaryDate.desc).to[List].result)

  def recent(limit: Int): Future[List[Diary]] = {
    val query = Diaries.sortBy(_.diaryDate.desc).take(limit)
    db.run(query.to[List].result)
  }

  def create(diary: Diary): Future[Long] =
    db.run(Diaries returning Diaries.map(_.id) += diary)

  def update(diary: Diary): Future[Int] =
    db.run(Diaries.update(diary))

  def delete(id: Long): Future[Int] =
    db.run(Diaries.filter(_.id === id).delete)

  private class DiariesTable(tag: Tag) extends Table[Diary](tag, "diaries") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def contents = column[String]("contents")
    def diaryDate = column[Option[Timestamp]]("diaryDate") //Option because DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

    def * = (id, contents, diaryDate) <> (Diary.tupled, Diary.unapply)
  }
}
