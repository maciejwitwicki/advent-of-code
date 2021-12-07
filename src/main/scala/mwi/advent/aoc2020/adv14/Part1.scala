package mwi.advent.aoc2020.adv14

import java.math.MathContext

import scala.collection.mutable

object Part1 {

  def solve(input: Array[String]) = {

    var tmpMask = ""
    val memory = mutable.HashMap.empty[Int, Long]

    for(line <- input) {

      var elems = line.split(" = ")
      if (elems(0) == "mask") {
        tmpMask = elems(1)
      } else {
        val adr = getAddress(elems(0))
        val adrInt = adr.toInt
        val value = elems(1).toInt

        val bitValue = padLeft(value.toBinaryString, 36)

        var tmpBitValue = bitValue.toCharArray
        for(i <- 1 to tmpBitValue.length) {
          val pointer = tmpBitValue.length - i
          val maskChar = tmpMask.charAt(pointer)
          if (maskChar != 'X') tmpBitValue(pointer) = maskChar
        }

        val newValueToWrite = BigInt(tmpBitValue.mkString, 2).longValue
        memory.put(adrInt, newValueToWrite)
      }
    }
    memory.foreach(println)
    val res = memory.values.sum
    println(s"result: $res")
  }

  private def getAddress(str: String) =
    {
      val endIndex = str.indexOf("]")
      str.substring(4, endIndex)
    }

  private def padLeft(str: String, len: Int) = {
    "".padTo(len - str.length,'0') + str
  }
}
