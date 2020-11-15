package services

import javax.inject._
import scala.io.Source
import java.security.SecureRandom
import scala.collection.mutable.ListBuffer

trait Words {
  def getWords(
      wordCount: Int,
      extraLength: Int,
      min: Option[Int],
      tries: Int
  ): List[String]
}

@Singleton
class WordList extends Words {
  private[this] val bufferedSource =
    Source.fromFile("./public/files/wordlist.txt")
  private[this] val wordList = bufferedSource.getLines().toList
  private[this] val wordListLength = wordList.length
  bufferedSource.close()

  def getWords(
      wordCount: Int,
      extraLength: Int,
      min: Option[Int],
      tries: Int
  ): List[String] = {
    var numWords = wordCount
    if (tries % 10 == 0) numWords += 1

    var randomWords = new ListBuffer[String]()
    (1 to numWords) foreach (_ => {
      randomWords += wordList(getRandomInt(wordListLength))
    })

    val wordLength = randomWords.mkString.length
    min match {
      case Some(minLength) => {
        if (wordLength < minLength - extraLength)
          return getWords(numWords, extraLength, min, tries + 1)
        else
          return randomWords.toList
      }
      case None =>
        return randomWords.toList
    }
  }

  private[this] def getRandomInt(maxlen: Int): Int = {
    val randomGen = SecureRandom.getInstance("SHA1PRNG", "SUN")
    return randomGen.nextInt(maxlen)
  }
}
