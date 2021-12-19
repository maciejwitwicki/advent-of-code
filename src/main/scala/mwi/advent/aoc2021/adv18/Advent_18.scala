package mwi.advent.aoc2021.adv18

import mwi.advent.aoc2021.App2021

object Advent_18 extends App2021 {

  val exampleString =
    """|[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
       |[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
       |""".stripMargin


  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(example)


}
