package mwi.advent.aoc2021.adv24

import mwi.advent.aoc2021.App2021

object Advent_24 extends App2021 {

  val exampleString =
    """inp w
      |add z w
      |mod z 2
      |div w 2
      |add y w
      |mod y 2
      |div w 2
      |add x w
      |mod x 2
      |div w 2
      |mod w 2
       |""".stripMargin

  val example = getInput(exampleString)
  val input   = getInput(this.getClass)

  Part1.solve(input)

}
