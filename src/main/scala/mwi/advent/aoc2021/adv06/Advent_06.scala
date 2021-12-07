package mwi.advent.aoc2021.adv06

import mwi.advent.aoc2021.App2021

object Advent_06 extends App2021 {

  val exampleString =
    """3,4,3,1,2
      |""".stripMargin

  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(example)
  Part2.solve(input)

}
