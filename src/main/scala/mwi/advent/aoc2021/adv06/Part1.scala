package mwi.advent.aoc2021.adv06

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 {

  def solve(input: Array[String]): Unit = {
    var fish = ArrayBuffer( input(0).split(",").map(Integer.parseInt):_* )

    val days = 80
    assert(days <= 80, "it will not work!")

    //fish = ArrayBuffer(1)

    println(s"Initial state: ${fish.mkString(",")}")
    for (d <- 1 to days) {
      for (i <- fish.indices) {

        val f = fish(i)


        if (f == 0) {
          fish(i) = 6
          fish.addOne(8)
        } else {
          fish(i) = f -1
        }
      }
      //println(s"After $d day: ${fish.mkString(",")}")
      if (d % 10 == 0) println(s"After $d day: ${fish.length} fish in the sea")
    }
    println(s"There are ${fish.size} fish in the sea")
  }
}
