package mwi.advent.aoc2021.adv21

import mwi.advent.aoc2021.App2021

object Advent_21 extends App2021 {

  val exampleString =
    """Player 1 starting position: 4
      |Player 2 starting position: 8
       |""".stripMargin

  val example = getInput(exampleString)
  val input   = getInput(this.getClass)

  Part1.solve(input)
  Part2.solve(input)

}
