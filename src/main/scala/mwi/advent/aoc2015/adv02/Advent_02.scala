package mwi.advent.aoc2015.adv02

import mwi.advent.aoc2015.App2015

object Advent_02 extends App2015 {

  val exampleString =
    """|2x3x4
       |1x1x10
      |""".stripMargin

  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(input)
  Part2.solve(input)

}
