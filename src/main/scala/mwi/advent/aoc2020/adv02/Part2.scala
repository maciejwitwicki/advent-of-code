package mwi.advent.aoc2020.adv02

object Part2 {

  def solve(input: Array[String]) = {

    val result = input.count(line => {

      val all = line.split(": ")
      val data = all(0)
      val str = all(1)
      val dataArray = data.split(' ')
      val rangePart = dataArray(0)
      val range = rangePart.split('-')
      val pos1 = range(0).toInt
      val pos2 = range(1).toInt
      val letter = dataArray(1)

      isEntryOk(pos1, pos2, letter.charAt(0), str)

    })

    println(s"Result $result")
  }

  private def isEntryOk(pos1: Int, pos2: Int, char: Char, line: String): Boolean = {
    (line(pos1 - 1) == char ^ line(pos2 - 1) == char)
  }


}
