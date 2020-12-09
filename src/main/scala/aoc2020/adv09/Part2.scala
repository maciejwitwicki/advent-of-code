package aoc2020.adv09

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part2 {


  def solve(input: Array[String], searchedNumber: BigDecimal) = {

    for (i <- 0 until input.length) {

      var tmpSum = BigDecimal(input(i))

      var nextNumIndex = i + 1


      var canProcess = true

      while (canProcess && nextNumIndex < input.length) {
        tmpSum = tmpSum + BigDecimal(input(nextNumIndex))
        if (tmpSum == searchedNumber) println(s"\n\n\nfound! ${input(i)} - ${input(nextNumIndex)} ie $i - $nextNumIndex ")
        nextNumIndex = nextNumIndex + 1
        canProcess = tmpSum < searchedNumber
      }

    }

    var numbers = ArrayBuffer.empty[BigDecimal]
    for (i <- 393 to 409)
      numbers.append(BigDecimal(input(i)))

    val res = numbers.min + numbers.max
    println(s"Result : $res")
  }
}

