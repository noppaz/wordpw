package services

import javax.inject._
import scala.io.Source
import scala.util.Random
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
  private val bufferedSource = Source.fromFile("./public/files/wordlist.txt")
  private val wordList = bufferedSource.getLines().toList
  private val wordListLength = wordList.length
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
      randomWords += wordList(Random.between(0, wordListLength))
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
}
