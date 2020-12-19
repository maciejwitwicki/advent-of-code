package aoc2020.adv20

import scala.io.Source

object Advent_20 extends App {

  val inputFileName = this.getClass.getSimpleName.replace("_", "-").replace("$", "").toLowerCase

  var inputFile: Array[String] = Source.fromResource(s"aoc2020/$inputFileName.txt").getLines().toArray

  val s1 =
    """0: 1 2
      |1: "a"
      |2: 1 3 | 3 1
      |3: "b"
      |""".stripMargin



   val input1 = Source.fromString(s1).getLines().toArray



   Part1.solve(input1)
   //Part2.solve(input1)
}
