package mwi.advent.aoc2015.adv04

import mwi.advent.aoc2015.App2015

object Advent_04 extends App2015 {

  val exampleString =
    """|abcdef
       |""".stripMargin

  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(input)


}
