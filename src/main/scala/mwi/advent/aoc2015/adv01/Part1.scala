package mwi.advent.aoc2015.adv01

object Part1 {

  def solve(input: Array[String]): Unit = {
    val result = input.head.map(ch => if (ch == '(') 1 else -1).sum
    println(s"floor: $result")
  }
}
