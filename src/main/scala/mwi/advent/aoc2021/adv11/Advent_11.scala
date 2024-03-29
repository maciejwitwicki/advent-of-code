package mwi.advent.aoc2021.adv11

import mwi.advent.aoc2021.App2021

object Advent_11 extends App2021 {

  val exampleString =
    """|5483143223
       |2745854711
       |5264556173
       |6141336146
       |6357385478
       |4167524645
       |2176841721
       |6882881134
       |4846848554
       |5283751526
      |""".stripMargin

  val exampleString2 =
    """|11111
       |19991
       |19191
       |19991
       |11111
      |""".stripMargin

  val example = getInput(exampleString)
  val example2 = getInput(exampleString2)
  val input = getInput(this.getClass)

  Part1.solve(input)
  Part2.solve(input)

}
