package mwi.advent.aoc2020.adv05

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 {

  def solve(input: Array[String]) = {

    val x = Integer.parseInt("100", 2)
    println(x)

    val maxi = input.map(line => {
      val row = line.substring(0, 7).replaceAll("F", "0").replaceAll("B", "1")
      val col = line.substring(7).replaceAll("L", "0").replaceAll("R", "1")

      println(s"row: $row col: $col")

      val rowInt = Integer.parseInt(row, 2)
      val colInt = Integer.parseInt(col, 2)
      println(s"row: $rowInt col: $colInt")

      val r = rowInt * 8 + colInt
      println(r)
      r
    }).max

    println(s"maxi: $maxi")

  }


}
