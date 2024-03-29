package mwi.advent.aoc2021.adv12

import mwi.advent.aoc2021.App2021
import mwi.advent.aoc2021.adv12.GraphBuilder.build

object Advent_12 extends App2021 {

  val exampleString =
    """|start-A
       |start-b
       |A-c
       |A-b
       |b-d
       |A-end
       |b-end
      |""".stripMargin


  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(input)
  Part2.solve(input)

}
