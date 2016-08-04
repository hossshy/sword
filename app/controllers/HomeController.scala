package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import akka.stream.Materializer
import play.api.i18n.{I18nSupport, MessagesApi}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()
  (implicit webJarAssets: WebJarAssets,
    val messagesApi: MessagesApi,
    materializer: Materializer)
    extends Controller with I18nSupport {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request =>
    Ok(views.html.index("Your new application is ready."))
  }

}
