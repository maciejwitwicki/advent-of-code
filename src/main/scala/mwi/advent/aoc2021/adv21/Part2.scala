package mwi.advent.aoc2021.adv21

import mwi.advent.aoc2021.adv19.impl.Parser
import mwi.advent.util.{Loc, AdventHelpers}

import java.math.BigInteger
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

object Part2 extends AdventHelpers {
  private implicit val ec: ExecutionContext = ExecutionContext.global

  var die = 0

  private var cache = mutable.HashMap.empty[(Int, Int, Int, Int), (Long, Long)]

  def solve(input: Array[String]): Unit = {

    val p1 = extractNumbers(input(0)).toList.last.toInt
    val p2 = extractNumbers(input(1)).toList.last.toInt

    val res = countWin(p1 - 1, p2 - 1, 0, 0)

    println(res)

  }

  private def countWin(p1: Int, p2: Int, s1: Int, s2: Int): (Long, Long) = {

    // return state if someone wins
    if (s1 >= 21) {
      (1L, 0L)
    } else if (s2 >= 21) {
      (0L, 1L)
    } else {
      cache.getOrElse(
        (p1, p2, s1, s2), {

          var res = (0L, 0L)

          for (d1 <- 1 to 3) {
            for (d2 <- 1 to 3) {
              for (d3 <- 1 to 3) {
                val newP1 = (p1 + d1 + d2 + d3) % 10
                val newS1 = s1 + newP1 + 1

                val (x1, y1) = countWin(p2, newP1, s2, newS1)
                res = (res._1 + y1, res._2 + x1)
              }
            }
          }
          cache.put((p1, p2, s1, s2), res)
          res
        }
      )
    }
  }
}
