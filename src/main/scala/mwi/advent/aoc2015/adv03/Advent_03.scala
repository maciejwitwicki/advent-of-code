package mwi.advent.aoc2015.adv03

import mwi.advent.aoc2015.App2015

object Advent_03 extends App2015 {

  val exampleString =
    """|^>v<
       |""".stripMargin

  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(input)
  Part2.solve(input)

}
