package aoc2021.adv02

import aoc2021.App2021

object Advent_02 extends App2021 {

  val s1 =
    """forward 5
      |down 5
      |forward 8
      |up 3
      |down 8
      |forward 2
      |""".stripMargin

  val input1 = getInput(s1)
  val input = getInput(this.getClass)

  Part1.solve(input)
  Part2.solve(input)

}
