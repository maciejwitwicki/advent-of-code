package mwi.advent.aoc2021.adv01

object Part1 {
  def solve(input: Array[String]) = {

    var numbers = input.map(Integer.parseInt)

    var increasing = numbers.sliding(2, 1)
      .map(window => {
        var a = window(0)
        var b = window(1)
//        println(s"comparing $a -> $b")
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
