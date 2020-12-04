package aoc2020.adv01

import scala.collection.mutable

object Solution {

  def solve1(input: Array[String]) = {
    val parsed = input.map(_.toLong)
    val x = parsed.map(2020 - _)
    val result = parsed.find(x.contains).map(z => (2020 - z) * z).getOrElse(0)
    println(s"Result $result")
  }

  def solve2(input: Array[String]) = {
    val parsed = input.map(_.toLong)

    val x: mutable.Map[Long, Entry] = mutable.HashMap()

    for(a <- parsed; b <- parsed; if a != b && a + b < 2020) {
      x.put(2020 - (a + b), Entry(a, b, a + b))
    }

    val result = parsed.find(x.contains).flatMap(x.get).map(e => (2020 - e.sum) * e.a * e.b)

    println(s"Result $result")
  }

}

case class Entry(a: Long, b: Long, sum: Long)
