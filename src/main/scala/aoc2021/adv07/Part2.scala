package aoc2021.adv07

import scala.collection.mutable.ArrayBuffer

object Part2 {

  def solve(input: Array[String]): Unit = {
    val positions = input.head.split(",").map(Integer.parseInt)
    val fuels = ArrayBuffer.empty[Int]

    for (i <- positions.indices) {
      val fuel = calculateForPos(i, positions)
      println(s"fuel for $i: $fuel")
      fuels.append(fuel)
    }
    val min = fuels.min
    println(s"minimum fuel: $min")
  }

  private def calculateForPos(i: Int, positions: Array[Int]): Int = {
    positions.map(p => Math.abs(p - i))
      .map(cost)
      .sum
  }

  private def cost(distance: Int): Int = {
    (1 to distance).sum
  }
}
