package mwi.advent.aoc2020.adv12

import scala.io.Source

object Advent_12 extends App {

  val inputFileName = this.getClass.getSimpleName.replace("_", "-").replace("$", "").toLowerCase

  var inputFile: Array[String] = Source.fromResource(s"mwi.aoc.aoc2020/$inputFileName.txt").getLines().toArray

  val s1 =
    """F10
      |N3
      |F7
      |R90
      |L180
      |R90
      |R90
      |F11
      |""".stripMargin


   val input1 = Source.fromString(s1).getLines().toArray

   //Part1.solve(inputFile)
   Part2.solve(inputFile) // 18789 to high!

}
