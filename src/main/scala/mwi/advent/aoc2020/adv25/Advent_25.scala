package mwi.advent.aoc2020.adv25

import scala.io.Source

object Advent_25 extends App {

  val inputFileName = this.getClass.getSimpleName.replace("_", "-").replace("$", "").toLowerCase

  var inputFile: Array[String] = Source.fromResource(s"mwi.aoc.aoc2020/$inputFileName.txt").getLines().toArray

  val s1 =
    """
      |""".stripMargin



   val input1 = Source.fromString(s1).getLines().toArray

   Part1.solve(input1)
//   Part2.solve(input1)
}
