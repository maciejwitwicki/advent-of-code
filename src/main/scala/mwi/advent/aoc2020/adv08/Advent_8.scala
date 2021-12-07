package mwi.advent.aoc2020.adv08

import scala.io.Source

object Advent_8 extends App {

  var inputFile: Array[String] = Source.fromResource("mwi/advent/aoc2020/advent-08.txt").getLines().toArray


  val s1 =
    """nop +0
      |acc +1
      |jmp +4
      |acc +3
      |jmp -3
      |acc -99
      |acc +1
      |jmp -4
      |acc +6
      |""".stripMargin


  val input1 = Source.fromString(s1).getLines().toArray

   //Part1.solve(inputFile)
   Part2.solve(inputFile)

}
