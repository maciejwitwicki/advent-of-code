package mwi.advent.aoc2020.adv25

object Part1 {
  def solve(input: Array[String]) = {
    val loopSearchSize = 100_000_000
    val printIteratorSize = 10_000_000
    val cardsPublicKey = BigDecimal(3469259)
    val doorsPublicKey = BigDecimal(13170438)
    val initialSubjectNumber = 7
    val divider = 20201227


    println(s"\nlooking for cards loop size\n")

    var cardLoopSize = -1
    var tmpValue: BigDecimal = 1
    var it = 0


    while (it < loopSearchSize && cardLoopSize < 0) {
      tmpValue = tmpValue * initialSubjectNumber
      tmpValue = tmpValue % divider

      it = it + 1

      if (it % printIteratorSize == 0) println(s"iteration $it")
      if (tmpValue == cardsPublicKey) {
        println(s"Woohoo the numbers match! card loop size is $it ")
        cardLoopSize = it
      }
    }

    println(s"\nlooking for doors loop size\n")


    it = 0
    tmpValue = 1
    while (it < loopSearchSize) {
      tmpValue = tmpValue * initialSubjectNumber
      tmpValue = tmpValue % divider

      it = it + 1
      if (it % printIteratorSize == 0) println(s"iteration $it")
      if (tmpValue == doorsPublicKey) println(s"Woohoo the numbers match! doors loop size is $it ")
    }

    tmpValue = 1
    it = 0
    while (it < cardLoopSize) {
      tmpValue = tmpValue * doorsPublicKey
      tmpValue = tmpValue % divider

      it = it + 1
    }

    println(s"after $cardLoopSize iterations the encryption key is $tmpValue")


  }
  }
