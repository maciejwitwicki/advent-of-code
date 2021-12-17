package mwi.advent.aoc2021.adv18

import mwi.advent.aoc2021.App2021

object Advent_18 extends App2021 {

  val exampleString =
    """|target area: x=20..30, y=-10..-5
       |""".stripMargin


  val example = getInput(exampleString)
  //val input = getInput(this.getClass)

  Part1.solve(example)


}
