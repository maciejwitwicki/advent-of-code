package mwi.advent.aoc2020.adv07

import scala.io.Source

object Advent_7 extends App {

  var inputFile: Array[String] = Source.fromResource("mwi/advent/aoc2020/advent-07.txt").getLines().toArray


  val s1 =
    """light red bags contain 1 bright white bag, 2 muted yellow bags.
      |dark orange bags contain 3 bright white bags, 4 muted yellow bags.
      |bright white bags contain 1 shiny gold bag.
      |muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
      |shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
      |dark olive bags contain 3 faded blue bags, 4 dotted black bags.
      |vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
      |faded blue bags contain no other bags.
      |dotted black bags contain no other bags.
      |""".stripMargin


  val input1 = Source.fromString(s1).getLines().toArray

   Part1.solve(input1)
   Part2.solve(inputFile)

}
