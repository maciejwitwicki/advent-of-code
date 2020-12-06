package aoc2020.adv06

import scala.io.Source

object Advent_5 extends App {

  var inputFile: Array[String] = Source.fromResource("aoc2020/advent-05.txt").getLines().toArray

  //218 to low

  val input1 = Source.fromString("FBFBBFFRLR").getLines().toArray

  val input2 = Array(
    "BFFFBBFRRR",
    "FFFBBBFRRR",
    "BBFFBBFRLL")

   Part1.solve(inputFile)
   Part2.solve(inputFile)



}


