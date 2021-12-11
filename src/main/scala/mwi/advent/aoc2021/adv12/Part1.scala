package mwi.advent.aoc2021.adv12

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ArrayStack}

object Part1 {

  val grid = mutable.HashMap.empty[Loc, Int]
  var maxX = 0
  var maxY = 0
  var totalFlashCount: Long = 0

  def solve(input: Array[String]): Unit = {
    parseInput(input);
    printGrid();

    val steps = 100

    for (x <- 0 until steps) {

      grid.foreach(e => grid(e._1) = e._2 + 1)

      handleFlashes(grid.filter(e => e._2 > 9).keys.toList)

      setFlashedToZero()

    }

    printGrid()
    println(s"total flash count ${totalFlashCount}")

  }

  private def setFlashedToZero(): Unit = {
    grid.filter(e => e._2 == -1)
    .foreach(e => {
      grid(e._1) = 0
      totalFlashCount += 1
    })
  }

  @tailrec
  private def handleFlashes(maxedLocs: List[Loc]): Int = {
    val toUpdate = maxedLocs
      .flatMap(loc => {

        grid(loc) = -1

        getAdjacent(loc)
      })

    toUpdate
      .filterNot(l => grid(l) == -1) // skip already flashed
      .foreach(l => grid(l) = grid(l) + 1)

    // find newly flashed
    val higherThanNine = grid.filter(e => e._2 > 9).keys.toList

    if (higherThanNine.isEmpty)
      0
    else
      handleFlashes(higherThanNine)
  }

  private def getAdjacent(loc: Part1.Loc) = {
    List(-1, 0, 1).flatMap(
      y => List(-1, 0, 1).map(x => {
        Loc(loc.x + x, loc.y + y)
      })
    ).filterNot(l =>
      (l.x == loc.x && l.y == loc.y) || (l.x < 0 || l.y < 0 || l.x > maxX || l.y > maxY)
    )
  }

  private def printGrid() = {
    println("== grid ==")
    for (y <- 0 to maxY) {

      var line = ""
      for (x <- 0 to maxX) {
        line += grid(Loc(x, y))
      }
      println(line)
    }
  }

  private def parseInput(input: Array[String]): Unit = {
    maxY = input.length - 1
    for (y <- 0 until input.length) {
      maxX = input(y).length - 1
      for (x <- 0 until input(y).length) {
        grid.put(Loc(x, y), Integer.parseInt("" + input(y)(x)))
      }
    }
  }

  case class Loc(x: Int, y: Int)

}
