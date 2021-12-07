package mwi.advent.aoc2020.adv22

import scala.collection.immutable.Queue
import scala.collection.mutable

object Part2 {
  def solve(input: Array[String]) = {

    val p1Deck = mutable.Queue.empty[Int]
    val p2Deck = mutable.Queue.empty[Int]

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

    // =============================== GAME! ===========================

    playRecursive(p1Deck, p2Deck, 0)

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

  private def playRecursive(deck1: mutable.Queue[Int], deck2: mutable.Queue[Int], depth: Int): Boolean = {
    println(s"Playing game at depth $depth")
    var it = 0
    var d1History = Set.empty[mutable.Queue[Int]]

    var infiniteLoopDetected = false
    while (deck1.nonEmpty && deck2.nonEmpty && !infiniteLoopDetected) {

      if (d1History.contains(deck1)) {
        println("!!! Warning, infinite loop detected !!!")
        infiniteLoopDetected = true
      } else {
        d1History = d1History + deck1.clone

        val p1Card = deck1.dequeue()
        val p2Card = deck2.dequeue()

        val recursion = shouldPlayRecursive(p1Card, deck1, p2Card, deck2)

        var p1toTake = Seq.empty[Int]
        var p2toTake = Seq.empty[Int]

        var p1Wins = false
        if (recursion) {
          p1Wins = playRecursive(deck1.take(p1Card), deck2.take(p2Card), depth + 1)
        } else {
          if (p1Card > p2Card) p1Wins = true
        }
        if (p1Wins)
          p1toTake = Seq(p1Card, p2Card)
        else
          p2toTake = Seq(p2Card, p1Card)


        deck1.appendAll(p1toTake)
        deck2.appendAll(p2toTake)

        it = it + 1
      }
    }

    deck1.nonEmpty
  }

  private def shouldPlayRecursive(c1: Int, d1: mutable.Queue[Int], c2: Int, d2: mutable.Queue[Int]) = {
    d1.size >= c1 && d2.size >= c2
  }
}
