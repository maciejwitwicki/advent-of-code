package mwi.advent.aoc2021.adv17

import mwi.advent.aoc2021.App2021

object Advent_17 extends App2021 {

  val exampleString =
    """|C200B40A82
       |04005AC33890
       |880086C3E88112
       |CE00C43D881120
       |D8005AC2A8F0
       |F600BC2D8F
       |9C005AC2F8F0
       |9C0141080250320F1802104A08
       |""".stripMargin


  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(example)

}
