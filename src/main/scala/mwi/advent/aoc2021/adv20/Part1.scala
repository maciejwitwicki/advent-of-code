package mwi.advent.aoc2021.adv20

import mwi.advent.aoc2021.adv19.impl.Parser
import mwi.advent.util.{Loc, AdventHelpers}

import java.math.BigInteger
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

object Part1 extends AdventHelpers {
  private implicit val ec: ExecutionContext = ExecutionContext.global

  private var bitmap = ""
  private var grid   = mutable.HashMap.empty[Loc, Char]
  private var maxX   = 0
  private var maxY   = 0
  private var minY   = 0
  private var minX   = 0

  def solve(input: Array[String]): Unit = {
    parseInput(input)
    printGrid()

    val steps = 50

    for (i <- 0 until steps) {
      enchanceGrid(i)
      printGrid()
    }

    val count = grid.values.count(c => c == '1')
    println(s"res: $count")
  }

  private def enchanceGrid(step: Int): Unit = {

    val newGrid = mutable.HashMap.empty[Loc, Char]

    val sqTop  = minY - 3
    val sqLeft = minX - 3

    val rangeY = buildRange(sqTop, maxY + 1)
    val rangeX = buildRange(sqLeft, maxX + 1)

    for (y <- rangeY) {
      for (x <- rangeX) {

        val bitWord  = readWordFromGrid(x, y, step)
        val index    = BigInt(bitWord, 2).toInt
        val newPixel = bitmap(index)
        val newBit = newPixel match {
          case '#'     => '1'
          case default => '0'
        }

        val sqCenter = Loc(x + 1, y + 1)
        newGrid.put(sqCenter, newBit)
      }
    }

    grid = newGrid
    minY = sqTop + 1
    minX = sqLeft + 1
    maxY = maxY + 1
    maxX = maxX + 1

  }

  private def readWordFromGrid(startX: Int, startY: Int, step: Int) = {

    var word = ""

    var y = startY
    var x = startX

    var defaultEmptyField = '0'

    if (bitmap(0) == '#') {
      defaultEmptyField = if (step % 2 == 0) '0' else '1'
    }

    while (y < startY + 3) {
      x = startX
      while (x < startX + 3) {
        val ch = grid.getOrElse(Loc(x, y), defaultEmptyField)
        word = word + ch
        x += 1
      }
      y += 1
    }

    word
  }

  private def parseInput(input: Array[String]): Unit = {
    bitmap = input(0)

    val img = input.drop(2)

    var y = 0
    maxY = img.length
    maxX = img(0).length
    while (y < maxY) {

      var x = 0

      while (x < maxX) {

        val ch  = img(y)(x)
        val bit = if (ch == '#') '1' else '0'
        grid.put(Loc(x, y), bit)

        x += 1
      }

      y += 1
    }
  }

  private def printGrid(): Unit = {

    println("== grid ==")
    val rangeY = buildRange(minY - 5, maxY + 5)
    val rangeX = buildRange(minX - 5, maxX + 5)
    for (y <- rangeY) {
      var line = ""
      for (x <- rangeX) {
        if (grid.getOrElse(Loc(x, y), '0') == '1') {
          line += "#"
        } else line += "."
        line += " "
      }
      println(line)
    }
  }

}
