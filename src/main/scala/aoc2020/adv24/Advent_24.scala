package aoc2020.adv24

import scala.io.Source

object Advent_24 extends App {

  val inputFileName = this.getClass.getSimpleName.replace("_", "-").replace("$", "").toLowerCase

  var inputFile: Array[String] = Source.fromResource(s"aoc2020/$inputFileName.txt").getLines().toArray

  val s1 =
    """389125467
      |""".stripMargin


  val s2 =
    """716892543
      |""".stripMargin



   val input1 = Source.fromString(s1).getLines().toArray
   val input2 = Source.fromString(s2).getLines().toArray

   //Part1.solve(input2)
   Part2.solve(input2) // 360 736 440 816 to low
}
