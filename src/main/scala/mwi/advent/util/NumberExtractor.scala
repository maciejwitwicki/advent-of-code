package mwi.advent.util

class NumberExtractor {
  private val Pattern = """(\d+)""".r

  def extractNumbers(input: String): Iterator[String] = {
    Pattern.findAllIn(input).matchData.flatMap(_.subgroups)
  }
  
}
