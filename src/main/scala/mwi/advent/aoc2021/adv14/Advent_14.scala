package mwi.advent.aoc2021.adv14

import mwi.advent.aoc2021.App2021

object Advent_14 extends App2021 {

  val exampleString =
    """|6,10
       |0,14
       |9,10
       |0,3
       |10,4
       |4,11
       |6,0
       |6,12
       |4,1
       |0,13
       |10,12
       |3,4
       |3,0
       |8,4
       |1,10
       |2,14
       |8,10
       |9,0
       |
       |fold along y=7
       |fold along x=5
      |""".stripMargin


  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(input)

}
