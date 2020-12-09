package aoc2020.adv09

import scala.io.Source

object Advent_09 extends App {

  val inputFileName = this.getClass.getSimpleName.replace("_", "-").replace("$", "").toLowerCase

  var inputFile: Array[String] = Source.fromResource(s"aoc2020/$inputFileName.txt").getLines().toArray

  val s1 =
    """35
      |20
      |15
      |25
      |47
      |40
      |62
      |55
      |65
      |95
      |102
      |117
      |150
      |182
      |127
      |219
      |299
      |277
      |309
      |576
      |""".stripMargin


  val input1 = Source.fromString(s1).getLines().toArray

//   Part1.solve(inputFile, 25)
   Part2.solve(inputFile, 22406676)
//   Part2.solve(input1, 127)

}
