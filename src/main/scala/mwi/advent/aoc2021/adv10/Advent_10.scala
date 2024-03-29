package mwi.advent.aoc2021.adv10

import mwi.advent.aoc2021.App2021

object Advent_10 extends App2021 {

  val exampleString =
    """|[({(<(())[]>[[{[]{<()<>>
       |[(()[<>])]({[<{<<[]>>(
       |{([(<{}[<>[]}>{[]{[(<()>
       |(((({<>}<{<{<>}{[]{[]{}
       |[[<[([]))<([[{}[[()]]]
       |[{[{({}]{}}([{[{{{}}([]
       |{<[[]]>}<{[{[{[]{()[[[]
       |[<(<(<(<{}))><([]([]()
       |<{([([[(<>()){}]>(<<{{
       |<{([{{}}[<[[[<>{}]]]>[]]
      |""".stripMargin

  val example = getInput(exampleString)
  val input = getInput(this.getClass)

  Part1.solve(input)
  Part2.solve(input)
}
