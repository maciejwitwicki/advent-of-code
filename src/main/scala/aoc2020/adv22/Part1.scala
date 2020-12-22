package aoc2020.adv22

import scala.collection.mutable


object Part1 {

  def solve(input: Array[String]) = {

    var p1Deck = mutable.Queue.empty[Int]
    var p2Deck = mutable.Queue.empty[Int]


    var it = 1
    while (input(it).nonEmpty) {

      val card = input(it).toInt
      p1Deck.append(card)

      it = it + 1
    }

    it = it + 2
    while (it < input.length) {

      val card = input(it).toInt
      p2Deck.append(card)

      it = it + 1
    }

    println("both decks ready, p1:")

    p1Deck.foreach(println)

    println("p2:")

    p2Deck.foreach(println)

    println("Playing the game")

    it = 0
    while (p1Deck.nonEmpty && p2Deck.nonEmpty) {

      val p1Card = p1Deck.dequeue()
      val p2Card = p2Deck.dequeue()

      if (p1Card > p2Card) p1Deck.appendAll(Seq(p1Card, p2Card))
      if (p2Card > p1Card) p2Deck.appendAll(Seq(p2Card, p1Card))

    it = it + 1
    }

    println("match ended!")
    val res = if (p1Deck.nonEmpty) p1Deck else p2Deck

    res.foreach(println)

    println("multypling")

    var sum = BigDecimal(0)
    var multiplier = res.length
    while (res.nonEmpty) {

      val card = BigDecimal(res.dequeue())
      sum = sum + (card * multiplier)

      multiplier = multiplier - 1

    }

    println(sum)

  }

}
