package mwi.advent.aoc2021.adv07

import mwi.advent.aoc2021.App2021

object Advent_07 extends App2021 {

  val exampleString =
    """16,1,2,0,4,2,7,1,2,14
      |""".stripMargin


  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(input)
  Part2.solve(input)

}
