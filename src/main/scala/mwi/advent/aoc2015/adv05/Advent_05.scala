package mwi.advent.aoc2015.adv05

import mwi.advent.aoc2015.App2015

object Advent_05 extends App2015 {

  val exampleString =
    """|ugknbfddgicrmopn
       |aaa
       |jchzalrnumimnmhp
       |haegwjzuvuyypxyu
       |dvszwmarrgswjxmb
       |""".stripMargin

  val part2exampleString =
    """|qjhvhtzxzqqjkmpb
       |xxyxx
       |uurcxstgmygtbstg
       |ieodomkazucvgmuy
       |""".stripMargin

  val example = getInput(exampleString)

  val input = getInput(this.getClass)

  Part1.solve(input)

  val part2example = getInput(part2exampleString)

  Part2.solve(input)


}
