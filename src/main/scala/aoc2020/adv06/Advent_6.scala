package aoc2020.adv06

import scala.io.Source

object Advent_6 extends App {

  var inputFile: Array[String] = Source.fromResource("aoc2020/advent-06.txt").getLines().toArray


  val s1 =
    """
      |abc
      |
      |a
      |b
      |c
      |
      |ab
      |ac
      |
      |a
      |a
      |a
      |a
      |
      |b
      |""".stripMargin


  val input1 = Source.fromString(s1).getLines().toArray

   Part1.solve(inputFile)
   Part2.solve(inputFile)

}


