package mwi.advent.aoc2021.adv08

object Part1 {

  def solve(input: Array[String]): Unit = {

    val result = input
      .map(line => {
        val temp = line.split(" \\| ")
        val signals = temp(0).split(" ")
        val output = temp(1).split(" ")
        Code(signals, output)
      })
      .map(c => c.getResult())
      .sum


    println(s"result: $result")
  }

  /**
   * 1 - 2 segments
   * 4 - 4 segments
   * 7 - 3 segments
   * 8 - 7 segments
   */

  var known = List(2, 4, 3, 7)

  case class Code(signals: Array[String], output: Array[String]) {

    def getResult() = {
      output.map(w => w.length)
        .count(l => {
          known.contains(l)
        })
    }
  }

}
