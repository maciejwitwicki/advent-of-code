package mwi.advent.aoc2021.adv08


import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part2 {

  def solve(input: Array[String]): Unit = {

    val result = input
      .map(line => {
        val temp = line.split(" \\| ")
        val signals = temp(0).split(" ")
        val output = temp(1).split(" ")
        Code(signals, output)
      })
      .map(c => c.getResult())
      .sum


    println(s"result: $result")
  }

  /**
   * 1 - 2 segments
   * 4 - 4 segments
   * 7 - 3 segments
   * 8 - 7 segments
   * 2 - 5 segments && contains 2 parts common with FOUR
   * 3 - 5 segments && contains 2 parts common with ONE
   * 5 - 5 segments && contains 3 parts common with FOUR
   * 6 - 6 segments && contains 1 part common with ONE && and all parts of FIVE
   * 9 - 6 segments && contains 2 parts common with ONE && and all parts of FIVE
   * 0 - 6 segments, the only one that has left
   */

  var known = List(2, 4, 3, 7)

  case class Code(signals: Array[String], output: Array[String]) {

    var basicMapping: mutable.HashMap[Int, String] = mutable.HashMap()

    calc()

    def getResult() = {
      println(s"decoding ${output.mkString(" ")}:")
      val resString = output.map(w => basicMapping.find((i, word) => w.sorted == word.sorted))
        .map(e => e.get)
        .map(e => e._1)
        .mkString

      resString.toInt
    }

    private def calc(): Unit = {

      basicMapping = mutable.HashMap(
        1 -> signals.find(w => w.length == 2).head,
        4 -> signals.find(w => w.length == 4).head,
        7 -> signals.find(w => w.length == 3).head,
        8 -> signals.find(w => w.length == 7).head
      )

      calculateFiveSegments()
      calculateSixSegments()
    }

    private def calculateFiveSegments() = {
      var fiveSegmentsLeft = ArrayBuffer(signals.filter(w => w.length == 5): _*)

      var three = fiveSegmentsLeft
        .find(w => getCommonCharsCount(1, w) == 2)
        .get

      basicMapping(3) = three

      fiveSegmentsLeft.remove(fiveSegmentsLeft.indexOf(three))

      var five = fiveSegmentsLeft
        .find(w => getCommonCharsCount(4, w) == 3)
        .get

      basicMapping(5) = five

      fiveSegmentsLeft.remove(fiveSegmentsLeft.indexOf(five))

      basicMapping(2) = fiveSegmentsLeft.head
    }

    private def calculateSixSegments() = {
      var sixSegmentsLeft = ArrayBuffer(signals.filter(w => w.length == 6): _*)

      val nine = sixSegmentsLeft
        .find(w => {
          var fiveLength = basicMapping(5).length
          getCommonCharsCount(5, w) == fiveLength && getCommonCharsCount(1, w) == 2
        }).get

      basicMapping(9) = nine

      sixSegmentsLeft.remove(sixSegmentsLeft.indexOf(nine))

      val six = sixSegmentsLeft
        .find(w => {
          var fiveLength = basicMapping(5).length
          getCommonCharsCount(5, w) == fiveLength
        })
        .get

      basicMapping(6) = six

      sixSegmentsLeft.remove(sixSegmentsLeft.indexOf(six))

      basicMapping(0) = sixSegmentsLeft.head

    }

    private def getCommonCharsCount(baseIndex: Int, other: String) = {
      var base = basicMapping(baseIndex)
      var baseChars = base.toArray
      var otherChars = other.toArray
      otherChars.filter(c => baseChars.contains(c)).length
    }

  }

}
