package services

import javax.inject._
import scala.io.Source
import scala.util.Random
import scala.collection.mutable.ListBuffer

trait Words {
  def getWords(wordCount: Int): List[String]
}

@Singleton
class WordList extends Words {
  private val bufferedSource = Source.fromFile("./public/files/wordlist.txt")
  private val wordList = bufferedSource.getLines().toList
  private val wordListLength = wordList.length
  bufferedSource.close()

  def getWords(wordCount: Int): List[String] = {
    var randomWords = new ListBuffer[String]()
    (1 to wordCount) foreach (_ => {
      randomWords += wordList(Random.between(0, wordListLength))
    })
    return randomWords.toList
  }
}
