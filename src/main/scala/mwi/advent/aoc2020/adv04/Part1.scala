package mwi.advent.aoc2020.adv04

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 {

  val requiredFields =  Set("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

  def solve(input: Array[String]) = {

    var fullInputs = List.empty[String]

    var tmpEntry = ""

    for (line <- input)
      if (line.nonEmpty) {
        tmpEntry = tmpEntry + line
      } else {
        fullInputs = fullInputs :+ tmpEntry
        tmpEntry = ""
      }

    if (tmpEntry.nonEmpty)
      fullInputs = fullInputs :+ tmpEntry

  println(fullInputs)
    println(s"tmp entry: $tmpEntry")

  val result = fullInputs.count(i => allFieldsPresent(i))
  println(s"Result $result")

  }

  private def allFieldsPresent(str: String) = {

    var ok = true

    for (f <- requiredFields) {
      if (!str.contains(s"$f:")) ok = false
    }

    ok
  }

}
