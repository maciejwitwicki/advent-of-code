package mwi.advent.aoc2021.adv03

import scala.annotation.tailrec

object Part2 {
  def solve(input: Array[String]) = {

    val oxygenRating = filterOxygen(input)
    val co2Rating = filterCO2(input)

    val oxygenInt = Integer.parseInt(oxygenRating, 2)
    val co2Int = Integer.parseInt(co2Rating, 2)

    println(s"oxygen: $oxygenRating ($oxygenInt)")
    println(s"co2: $co2Rating ($co2Int)")
    println(s"result: ${co2Int * oxygenInt}")
  }

  private def filterOxygen(input: Array[String]): String = {
    filter(input, 0, oxygenCriteria).head
  }

  private def filterCO2(input: Array[String]): String = {
    filter(input, 0, co2Criteria).head
  }

  @tailrec
  private def filter(input: Array[String],
                     position: Int,
                     criteriaFn: (Int, Int) => Char): Array[String] = {
    val threshold = Math.round(input.length / 2.0).toInt

    val onesCount = input.map(line => line(position)).count(c => c == '1')

    val criteria = criteriaFn.apply(onesCount, threshold)

    val filtered = input.filter(line => line.charAt(position) == criteria)

    // println(s"filtered: ${filtered.mkString(", ")}")

    if (filtered.length == 1)
      filtered
    else
      filter(filtered, position + 1, criteriaFn)
  }

  private def co2Criteria(onesCount: Int, threshold: Int): Char = {
    if (onesCount < threshold) '1' else '0'
  }
  private def oxygenCriteria(onesCount: Int, threshold: Int): Char = {
    if (onesCount >= threshold) '1' else '0'
  }
}
