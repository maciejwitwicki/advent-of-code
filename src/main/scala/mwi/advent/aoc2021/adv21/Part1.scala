package mwi.advent.aoc2021.adv21

import mwi.advent.aoc2021.adv19.impl.Parser
import mwi.advent.util.{Loc, AdventHelpers}

import java.math.BigInteger
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

object Part1 extends AdventHelpers {
  private implicit val ec: ExecutionContext = ExecutionContext.global

  var die = 0

  def solve(input: Array[String]): Unit = {

    var p1 = extractNumbers(input(0)).toList.last.toInt
    var p2 = extractNumbers(input(1)).toList.last.toInt

    var p1Score = 0
    var p2Score = 0

    var turns = 0

    while (p1Score < 1000 && p2Score < 1000) {

      val thrown1 = nextDices()
      val nextP1  = p1 + thrown1.sum

      if (nextP1 > 10) {
        p1 = nextP1 % 10
        if (p1 == 0) p1 = 10
      } else {
        p1 = nextP1
      }
      p1Score += p1

      println(s"Player 1 rolls for ${thrown1.mkString("+")} and moves to $p1 for total $p1Score")

      if (p1Score < 1000) {

        val thrown2 = nextDices()
        val nextP2  = p2 + thrown2.sum

        if (nextP2 > 10) {
          p2 = nextP2 % 10
          if (p2 == 0) p2 = 10
        } else {
          p2 = nextP1
        }
        p2Score += p2

        println(s"Player 2 rolls for ${thrown2.mkString("+")} and moves to $p2 for total $p2Score")

      }

      turns += 1
    }

    val dieThrows = if (p1Score >= 1000) {
      turns * 6 - 3
    } else {
      turns * 6
    }

    println(s"$turns: p1: $p1Score, p2: $p2Score")
    val min = Math.min(p1Score, p2Score)
    val res = dieThrows * min

    println(s"res: $res")

  }

  private def nextDices() = {

    var rolls = ArrayBuffer.empty[Int]

    for (i <- 0 until 3) {

      die += 1

      if (die > 100) die = 1

      rolls.append(die)
    }

    rolls

  }

}
