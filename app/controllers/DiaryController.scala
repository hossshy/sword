package controllers

import models.DiaryRepo
import javax.inject._
import play.api._
import play.api.mvc._
import akka.stream.Materializer
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

@Singleton
class DiaryController @Inject()
  (implicit webJarAssets: WebJarAssets,
    val messagesApi: MessagesApi,
    materializer: Materializer,
    diaryRepo: DiaryRepo)
    extends Controller with I18nSupport {

  def index = Action.async { implicit request =>
    diaryRepo.recent(20).map { diaries =>
      Ok(views.html.diary(diaries))
    }
  }
}
