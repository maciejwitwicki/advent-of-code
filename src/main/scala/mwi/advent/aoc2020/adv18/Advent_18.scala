package mwi.advent.aoc2020.adv18

import scala.io.Source

object Advent_18 extends App {

  val inputFileName = this.getClass.getSimpleName.replace("_", "-").replace("$", "").toLowerCase

  var inputFile: Array[String] = Source.fromResource(s"mwi.aoc.aoc2020/$inputFileName.txt").getLines().toArray

  val s1 =
    """1 + (2 * 3) + (4 * (5 + 6))
      |2 * 3 + (4 * 5)
      |5 + (8 * 3 + 9 + 3 * 4 * 3)
      |5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))
      |((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2
      |""".stripMargin

   val input1 = Source.fromString(s1).getLines().toArray

   //Part1.solve(inputFile)
   Part2.solve(inputFile)

}
