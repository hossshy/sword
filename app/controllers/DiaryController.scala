package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import akka.stream.Materializer
import play.api.i18n.{I18nSupport, MessagesApi}

@Singleton
class DiaryController @Inject()
  (implicit webJarAssets: WebJarAssets,
    val messagesApi: MessagesApi,
    materializer: Materializer)
    extends Controller with I18nSupport {

  def index = Action { implicit request =>
    Ok(views.html.diary("hogehoge."))
  }
}
