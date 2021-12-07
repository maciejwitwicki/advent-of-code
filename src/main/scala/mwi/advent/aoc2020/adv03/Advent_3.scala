package mwi.advent.aoc2020.adv03

import scala.io.Source

object Advent_3 extends App {

  var inputFile: Array[String] = Source.fromResource("mwi/advent/aoc2020/advent-03.txt").getLines().toArray

  val input1 = Array(
    "..##.......",
    "#...#...#..",
    ".#....#..#.",
    "..#.#...#.#",
    ".#...##..#.",
    "..#.##.....",
    ".#.#.#....#",
    ".#........#",
    "#.##...#...",
    "#...##....#",
    ".#..#...#.#"
  )

   Part1.solve1(inputFile)


}
