package mwi.advent.aoc2020.adv22

import scala.io.Source

object Advent_22 extends App {

  val inputFileName = this.getClass.getSimpleName.replace("_", "-").replace("$", "").toLowerCase

  var inputFile: Array[String] = Source.fromResource(s"mwi.aoc.aoc2020/$inputFileName.txt").getLines().toArray

  val s1 =
    """Player 1:
      |9
      |2
      |6
      |3
      |1
      |
      |Player 2:
      |5
      |8
      |4
      |7
      |10
      |""".stripMargin


  val s2 =
    """Player 1:
      |43
      |19
      |
      |Player 2:
      |2
      |29
      |14
      |""".stripMargin



   val input1 = Source.fromString(s1).getLines().toArray
   val input2 = Source.fromString(s2).getLines().toArray

//   Part1.solve(inputFile)
   Part2.solve(inputFile)
}
