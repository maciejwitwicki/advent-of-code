package aoc2020.adv10

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part2 {

  var combsForLenght = mutable.HashMap.empty[Int, Int]


  def solve(input: Array[String]) = {
    val ints: Array[Int] = input.map(Integer.parseInt)

    val device: Int = ints.max + 3
    val outlet: Int = 0

    val withDevice: Array[Int] = ints :+ device
    val withOutlet: Array[Int] = withDevice :+ outlet

    val sorted = withOutlet.sorted

    var combinations = List.empty[Int]

    var iterator = 0
    var currentWordLength = 1
    while (iterator < sorted.length - 1) {

      val curAdapter = sorted(iterator)
      val nextAdapter = sorted(iterator + 1)
      if (nextAdapter - curAdapter == 1) {
        currentWordLength = currentWordLength + 1
      } else {
        // nastepny jest oddalony o 3, zamykamy slowo i dodajemy ilosc kombinacji
        combinations = combinations :+ getCombinationCountForLength(currentWordLength)
        currentWordLength = 1
      }

      iterator = iterator + 1
    }
    // dodajmy ostatnią kombinację
    if (currentWordLength > 0) combinations = combinations :+ getCombinationCountForLength(currentWordLength)

    println(s"combinations: $combinations")
    val res = combinations.foldLeft(BigDecimal(1))(_ * _)
    println(s"result $res")
  }

  // binary generator of combinations
  private def getCombinationCountForLength(len: Int) = {

    combsForLenght.getOrElse(len, {

      val power = len
      val max = Math.pow(2, power).intValue()

      var list = List.empty[String]
      for (i <- 0 until max) {
        val str = padLeft(Integer.toBinaryString(i), power).replace('0', '-')
        list = list :+ str
      }

      val modList = list.filter(s => {
        s.charAt(0) == '1' && s.charAt(s.length - 1) == '1' && !s.contains("---")
      })

      println(modList.mkString("\n"))
      println(s"for $len adapters there are ${modList.size} combinations")

      combsForLenght.put(len, modList.size)
      modList.size
    })
  }

  private def padLeft(str: String, len: Int) = {
    "".padTo(len - str.length,'0') + str
  }

}

