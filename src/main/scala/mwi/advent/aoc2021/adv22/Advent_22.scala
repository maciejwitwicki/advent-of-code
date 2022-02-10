package mwi.advent.aoc2021.adv22

import mwi.advent.aoc2021.App2021

object Advent_22 extends App2021 {

  val exampleString =
    """on x=10..12,y=10..12,z=10..12
      |on x=11..13,y=11..13,z=11..13
       |""".stripMargin

  val example = getInput(exampleString)
  val input   = getInput(this.getClass)

//  Part1.solve(example)
  Part2.solve(example)

}
