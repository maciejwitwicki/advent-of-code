package aoc2021.adv06

import aoc2021.App2021

object Advent_06 extends App2021 {

  val exampleString =
    """0,9 -> 5,9
      |8,0 -> 0,8
      |9,4 -> 3,4
      |2,2 -> 2,1
      |7,0 -> 7,4
      |6,4 -> 2,0
      |0,9 -> 2,9
      |3,4 -> 1,4
      |0,0 -> 8,8
      |5,5 -> 8,2
      |""".stripMargin

  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(example)

}
