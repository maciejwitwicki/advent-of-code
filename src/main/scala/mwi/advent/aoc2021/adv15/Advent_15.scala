package mwi.advent.aoc2021.adv15

import mwi.advent.aoc2021.App2021

object Advent_15 extends App2021 {

  val exampleString =
    """|NNCB
       |
       |CH -> B
       |HH -> N
       |CB -> H
       |NH -> C
       |HB -> C
       |HC -> B
       |HN -> C
       |NN -> C
       |BH -> H
       |NC -> B
       |NB -> B
       |BN -> B
       |BB -> N
       |BC -> B
       |CC -> N
       |CN -> C
       |""".stripMargin


  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(example)


}
