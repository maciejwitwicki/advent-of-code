
package mwi.advent.aoc2021.adv18

import mwi.advent.util.{Loc, NumberExtractor}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 extends NumberExtractor {

  def solve(input: Array[String]): Unit = {

    val line = input(0)
    val groups = extractNumbers(line)
    println(groups.mkString(", "))

  }


}
