package mwi.advent.aoc2021.adv23

import mwi.advent.aoc2021.App2021

object Advent_23 extends App2021 {

  val exampleString =
    """#############
      |#...........#
      |###B#C#B#D###
      |  #A#D#C#A#
      |  #########
       |""".stripMargin

  val example = getInput(exampleString)
  val input   = getInput(this.getClass)

  Part1.solve(example)

}
