package aoc2020.adv10

import scala.io.Source

object Advent_10 extends App {

  val inputFileName = this.getClass.getSimpleName.replace("_", "-").replace("$", "").toLowerCase

  var inputFile: Array[String] = Source.fromResource(s"aoc2020/$inputFileName.txt").getLines().toArray

  val s1 =
    """16
      |10
      |15
      |5
      |1
      |11
      |7
      |19
      |6
      |12
      |4
      |""".stripMargin

  val s2 =
    """28
      |33
      |18
      |42
      |31
      |14
      |46
      |20
      |48
      |47
      |24
      |23
      |49
      |45
      |19
      |38
      |39
      |11
      |1
      |32
      |25
      |35
      |8
      |17
      |7
      |9
      |4
      |2
      |34
      |10
      |3
      |""".stripMargin


  val input1 = Source.fromString(s1).getLines().toArray
  val input2 = Source.fromString(s2).getLines().toArray

   //Part1.solve(input1)
   Part2.solve(inputFile)

}
