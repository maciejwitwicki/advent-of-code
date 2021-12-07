package mwi.advent.aoc2020.adv02

object Part1 {

  def solve1(input: Array[String]) = {

    val result = input.count(line => {

      val all = line.split(": ")
      val data = all(0)
      val str = all(1)
      val dataArray = data.split(' ')
      val rangePart = dataArray(0)
      val range = rangePart.split('-')
      val min = range(0).toInt
      val max = range(1).toInt
      val letter = dataArray(1)

      isEntryOk1(min, max, letter.charAt(0), str)

    })

    println(s"Result $result")
  }

  private def isEntryOk1(min: Int, max: Int, char: Char, line: String): Boolean = {

    var count = 0
    for (i <- line) {
      if (i == char) count = count + 1
    }
    count >= min && count <= max
  }

}
