package mwi.advent.aoc2021.adv12

import mwi.advent.aoc2021.App2021

object Advent_12 extends App2021 {

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

  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(example)


}
