package controllers

import play.api._
import play.api.mvc._
import javax.inject._
import java.security.SecureRandom
import services.Words

@Singleton
class HomeController @Inject() (cc: ControllerComponents, words: Words)
    extends AbstractController(cc) {

  def index() =
    Action { implicit request: Request[AnyContent] =>
      Redirect("/pw?words=4&sep=.&end=_&numbers=2")
    }

  def pw(
      wordCount: Int,
      sep: String,
      end: String,
      numbers: Int,
      min: Option[Int]
  ) =
    Action { implicit request: Request[AnyContent] =>
      val extraLength = sep.length * (wordCount - 1) + end.length + numbers
      val wordList = words.getWords(wordCount, extraLength, min, 1)
      val wordString = wordList.mkString(sep)

      val randomGen = SecureRandom.getInstance("SHA1PRNG", "SUN")
      val n = randomGen.nextDouble
      val exponent = if (n > 0.1) numbers else numbers + 1
      val numberSeq =
        String.format("%.0f", n * math.pow(10, exponent))

      val password = wordString.capitalize + end + numberSeq.toString
      Ok(views.html.pw(password.length, wordList.length, password))
    }
}
