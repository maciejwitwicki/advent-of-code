package aoc2021.adv07

import aoc2021.App2021

object Advent_07 extends App2021 {

  val exampleString =
    """3,4,3,1,2
      |""".stripMargin

  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(example)

}
