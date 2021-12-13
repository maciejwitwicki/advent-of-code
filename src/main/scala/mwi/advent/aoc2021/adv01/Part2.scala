package mwi.advent.aoc2021.adv01

object Part2 {
  def solve(input: Array[String]) = {

    val numbers = input.map(Integer.parseInt)

    val increasing = numbers.sliding(3, 1)
      .map(window => {
        val a = window(0)
        val b = window(1)
        val c = window(2)
        a + b + c
      })
      .sliding(2, 1)
      .map(window => {
        val a = window(0)
        val b = window(1)

        if (b > a) {
          1
        } else {
          0
        }
      })
      .sum

    println(s"Increasing $increasing")

  }
}
