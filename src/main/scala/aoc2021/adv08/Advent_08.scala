package aoc2021.adv08

import aoc2021.App2021

object Advent_08 extends App2021 {

  val exampleString =
    """16,1,2,0,4,2,7,1,2,14
      |""".stripMargin

  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(example)


}
