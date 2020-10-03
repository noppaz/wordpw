package controllers

import play.api._
import play.api.mvc._
import javax.inject._
import scala.util.Random
import services.Words

@Singleton
class HomeController @Inject() (cc: ControllerComponents, words: Words)
    extends AbstractController(cc) {

  def index() =
    Action { implicit request: Request[AnyContent] =>
      Redirect("/pw?words=4&sep=.&end=_&numbers=2")
    }

  def pw(wordCount: Int, sep: String, end: String, numbers: Int) =
    Action { implicit request: Request[AnyContent] =>
      val wordList = words.getWords(wordCount)
      val wordString = wordList.mkString(sep)
      val numberSeq = (Random.between(0.1, 1.0) * math.pow(10, numbers)).toInt
      val password = wordString.capitalize + end + numberSeq.toString
      Ok(views.html.pw(password.length, wordCount, password))
    }
}