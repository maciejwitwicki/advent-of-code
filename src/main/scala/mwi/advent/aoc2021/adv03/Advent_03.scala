package mwi.advent.aoc2021.adv03

import mwi.advent.aoc2021.App2021

object Advent_03 extends App2021 {

  val s1 =
    """00100
      |11110
      |10110
      |10111
      |10101
      |01111
      |00111
      |11100
      |10000
      |11001
      |00010
      |01010
      |""".stripMargin

  val example = getInput(s1)

  val input = getInput(this.getClass)

  Part1.solve(input)
  Part2.solve(input)


}
