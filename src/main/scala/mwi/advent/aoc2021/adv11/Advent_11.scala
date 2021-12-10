package mwi.advent.aoc2021.adv11

import mwi.advent.aoc2021.App2021

object Advent_11 extends App2021 {

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

  Part1.solve(example)

}
