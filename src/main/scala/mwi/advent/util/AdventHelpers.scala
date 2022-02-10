package mwi.advent.util

import scala.collection.mutable.ArrayBuffer

class AdventHelpers {
  private val Pattern = """(-?\d+)""".r

  def extractNumbers(input: String): Iterator[String] = {
    Pattern.findAllIn(input).matchData.flatMap(_.subgroups)
  }

  def buildRange(min: Int, max: Int): ArrayBuffer[Int] = {
    var i      = min
    val result = ArrayBuffer.empty[Int]
    while (i < max) {
      result.append(i)
      i += 1
    }
    result
  }

}
