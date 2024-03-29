package mwi.advent.aoc2021.adv09

import mwi.advent.aoc2021.App2021

object Advent_09 extends App2021 {

  val exampleString =
    """|2199943210
       |3987894921
       |9856789892
       |8767896789
       |9899965678
      |""".stripMargin

  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(input)
  Part2.solve(input)



}
