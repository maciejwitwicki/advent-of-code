package mwi.advent.aoc2021.adv16

import mwi.advent.aoc2021.App2021

object Advent_16 extends App2021 {

  val exampleString =
    """|1163751742
       |1381373672
       |2136511328
       |3694931569
       |7463417111
       |1319128137
       |1359912421
       |3125421639
       |1293138521
       |2311944581
       |""".stripMargin


  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(example)



}
