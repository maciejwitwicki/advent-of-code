package aoc2020.adv14

import scala.collection.mutable

object Part2 {

  def solve(input: Array[String]) = {

    var tmpMask = ""
    val memory = mutable.HashMap.empty[String, Long]

    for(line <- input) {

      val elems = line.split(" = ")
      if (elems(0) == "mask") {
        tmpMask = elems(1)
      } else {
        val adr = getAddress(elems(0))
        val adrInt = adr.toInt
        val adrBin = padLeft(adrInt.toBinaryString, 36)

        val valueToWrite = elems(1).toInt


        var tmpAdrToWrite = adrBin.toCharArray
        for(i <- 1 to tmpAdrToWrite.length) {
          val pointer = tmpAdrToWrite.length - i
          val maskChar = tmpMask.charAt(pointer)
          maskChar match {
            case 'X' => tmpAdrToWrite(pointer) = 'X'
            case '0' =>
            case '1' => tmpAdrToWrite(pointer) = '1'
          }
        }

        val addresses = generate(tmpAdrToWrite.mkString)

        addresses.foreach(adr => memory.put(adr, valueToWrite))
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


  private def generate(bitStr: String): List[String] = {
    val index = bitStr.indexOf('X')
    if (index > -1) {
      val (part1, p2) = bitStr.splitAt(index)
      val part2 = p2.substring(1)
      val result = List(
        part1 + "0" + part2,
        part1 + "1" + part2
      )
      result.flatMap(generate)
    } else {
      List(bitStr)
    }
  }
}

