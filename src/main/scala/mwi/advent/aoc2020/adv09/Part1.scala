package mwi.advent.aoc2020.adv09

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object Part1 {


  def solve(input: Array[String], preambleLength: Int) = {


    var sums = mutable.HashMap.empty[Int, Set[BigDecimal]]

    for (inputIndex <- preambleLength until input.length) {

      for (firstNumberIndex <- 0 until preambleLength - 1) {

        val aIndex = inputIndex - preambleLength + firstNumberIndex
        val a = BigDecimal(input(aIndex))

        for (secondNumberIndex <- firstNumberIndex + 1 until preambleLength) {
          val bIndex = inputIndex - preambleLength + secondNumberIndex
          val b = BigDecimal(input(bIndex))

          if (a != b) {
            val dek = sums.getOrElse(firstNumberIndex, Set.empty[BigDecimal])
            val newDek = dek + (a + b)
            sums(firstNumberIndex) = newDek
          }

        }

      }

      // check if i is valid
      val testedValue = BigDecimal(input(inputIndex))
      var valid = false
      var it = 0
      while (!valid && it < preambleLength - 1) {
        if (sums(it).contains(testedValue)) {
          valid = true
        }

        it = it + 1
      }

      println(s" $testedValue is $valid")


      val newSums = mutable.HashMap.empty[Int, Set[BigDecimal]]
      for (rot <- 0 until preambleLength - 2) {
        newSums(rot) = sums(rot + 1)
      }

      sums = newSums

    }



  }





}
