package mwi.advent.aoc2021.adv01

import mwi.advent.aoc2021.App2021

object Advent_01 extends App2021 {

  var inputFile = getInput(this.getClass)

  val s1 =
    """199
      |200
      |208
      |210
      |200
      |207
      |240
      |269
      |260
      |263
      |""".stripMargin


   val input1 = getInput(s1)

   Part1.solve(inputFile)
   Part2.solve(inputFile)
}
