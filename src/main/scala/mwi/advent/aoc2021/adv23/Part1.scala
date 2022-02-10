package mwi.advent.aoc2021.adv23

import mwi.advent.aoc2021.adv19.impl.Parser
import mwi.advent.util.{AdventHelpers, Loc, Loc3d}

import java.math.BigInteger
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

// unfinished
object Part1 extends AdventHelpers {

  private implicit val ec: ExecutionContext = ExecutionContext.global

  private val grid = mutable.HashMap.empty[Loc, Char]

  def solve(input: Array[String]): Unit = {

    parseInput(input)

    printGrid()

    val steps = 10
    var n     = 0
    while (n < steps) {

      n += 1
    }

  }

  private def printGrid() = {
    println("== grid ==")
    for (y <- 0 to 2) {
      var str = ""
      for (x <- 0 to 10) {
        str += grid.getOrElse(Loc(x, y), ' ')
      }
      println(str)
    }
  }

  private def parseInput(input: Array[String]) = {

    for (x <- 0 to 10) {
      grid.put(Loc(x, 0), '.')
    }

    val line1 = input(2)
    grid.put(Loc(2, 1), line1(3))
    grid.put(Loc(4, 1), line1(5))
    grid.put(Loc(6, 1), line1(7))
    grid.put(Loc(8, 1), line1(9))

    val line2 = input(3)
    grid.put(Loc(2, 2), line2(3))
    grid.put(Loc(4, 2), line2(5))
    grid.put(Loc(6, 2), line2(7))
    grid.put(Loc(8, 2), line2(9))

  }

}
