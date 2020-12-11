package aoc2020.adv10

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object Part1 {


  def solve(input: Array[String]) = {
    val ints: Array[Int] = input.map(Integer.parseInt)

    val device: Int = ints.max + 3
    val outlet: Int = 0

    val withDevice: Array[Int] = ints :+ device
    val withOutlet: Array[Int] = withDevice :+ outlet

    val sorted = withOutlet.sorted

    var diffs1 = 0
    var diffs3 = 0

    for (i <- 0 until sorted.length - 1) {
      if (sorted(i + 1) - sorted(i) == 1) diffs1 = diffs1 + 1
      if (sorted(i + 1) - sorted(i) == 3) diffs3 = diffs3 + 1
    }

    println(s"diffs 1 $diffs1")
    println(s"diffs 3 $diffs3")
    val result = diffs3 * diffs1
    println(s"res: $result")



  }


}


