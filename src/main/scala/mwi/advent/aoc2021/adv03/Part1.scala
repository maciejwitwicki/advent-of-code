package mwi.advent.aoc2021.adv03

import scala.collection.mutable

object Part1 {
  def solve(input: Array[String]): Unit = {

    val wordLength = input(0).length
    val list = mutable.ArrayBuffer.fill(wordLength)(0)

    input.foreach(line => {
      var x = 0
      line.foreach(ch => {
        if (ch == '1') {
          val prev = list(x)
          list(x) = prev + 1
        }
        x = x + 1
      })
    })

    val threshold = input.length / 2

    val gamma = list.map(i => if (i > threshold) "1" else "0").mkString
    val gammaInt = Integer.parseInt(gamma, 2)

    val epsilon = flip(gamma)
    val epsilonInt = Integer.parseInt(epsilon, 2)

    println(s"gamma: $gamma, epsilon: $epsilonInt, result: ${gammaInt * epsilonInt}")

  }

  private def flip(input: String): String = input.map(i => if (i == '0') '1'else '0')
}
