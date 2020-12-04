package aoc2020.adv02

import scala.io.Source

object Advent_2_1 extends App {

  var inputFile: Array[String] = Source.fromResource("aoc2020/advent-02.txt").getLines().toArray

  val input1 = Array(
    "1-3 a: abcde",
    "1-3 b: cdefg",
    "2-9 c: ccccccccc"
  )

   Part1.solve1(input1)
   Part2.solve(inputFile)

}


