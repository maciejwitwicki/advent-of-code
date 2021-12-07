package mwi.advent.aoc2020.adv01

import scala.io.Source

object Advent_1_1 extends App {

  var inputFile: Array[String] = Source.fromResource("mwi/advent/aoc2020/advent-01.txt").getLines().toArray

  val input1 = Array(
    "1721",
    "979",
    "366",
    "299",
    "675",
    "1456"
  )

   Solution.solve2(inputFile)

}
