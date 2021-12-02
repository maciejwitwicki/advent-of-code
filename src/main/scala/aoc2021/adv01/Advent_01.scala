package aoc2021.adv01

import scala.io.Source

object Advent_01 extends App {

  val inputFileName = this.getClass.getSimpleName.replace("_", "-").replace("$", "").toLowerCase

  var inputFile: Array[String] = Source.fromResource(s"aoc2021/$inputFileName.txt").getLines().toArray

  val s1 =
    """199
      |200
      |208
      |210
      |200
      |207
      |240
      |269
      |260
      |263
      |""".stripMargin


   val input1 = Source.fromString(s1).getLines().toArray

   Part1.solve(inputFile)
   Part2.solve(inputFile)
}
