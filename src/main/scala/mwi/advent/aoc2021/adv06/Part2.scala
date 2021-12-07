package mwi.advent.aoc2021.adv06

import scala.collection.mutable.ArrayBuffer

object Part2 {

  def solve(input: Array[String]): Unit = {
    val fish = ArrayBuffer( input(0).split(",").map(Integer.parseInt):_* )

    val days = 256
    println(s"Initial state: ${fish.mkString(",")}")
    var states: ArrayBuffer[Long] = ArrayBuffer.fill(9)(0L)
    for (f <- fish) {
      states(f) = states(f) + 1
    }

    println(s" day 0: ${states.mkString(",")} ")
    for (d <- 1 to days) {
      val newStates: ArrayBuffer[Long] = ArrayBuffer.from(states)

      for (s <- states.indices) {

        s match {
          case 0 | 1 | 2 | 3 | 4 | 5 | 7 => newStates(s) = states(s + 1)
          case 6 => newStates(6) = states(7) + states(0)
          case 8 => newStates(8) = states(0)
        }

      }
      states = newStates
      //println(s" day $d: ${states.mkString(",")} ")
    }

    println(s"all fish: ${states.sum}")
  }


}
