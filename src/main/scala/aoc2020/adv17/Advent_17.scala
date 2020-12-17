package aoc2020.adv17

import scala.io.Source

object Advent_17 extends App {

  val inputFileName = this.getClass.getSimpleName.replace("_", "-").replace("$", "").toLowerCase

  var inputFile: Array[String] = Source.fromResource(s"aoc2020/$inputFileName.txt").getLines().toArray

  val s1 =
    """.#.
      |..#
      |###
      |""".stripMargin

   val input1 = Source.fromString(s1).getLines().toArray

   Part1.solve(inputFile) // 244 to low
   Part2.solve(input1)

}
