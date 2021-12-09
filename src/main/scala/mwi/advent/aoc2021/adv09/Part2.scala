package mwi.advent.aoc2021.adv09

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part2 {

  var grid: mutable.Map[Loc, Int] = mutable.HashMap()
  var pointsToCheck: ArrayBuffer[Loc] = new ArrayBuffer()
  var maxX = 0
  var maxY = 0

  def solve(input: Array[String]): Unit = {


    val intInput = input.map(line => {
      line.toArray.map(c => Integer.parseInt(s"$c"))
    })

    maxX = intInput(0).length - 1
    maxY = intInput.length - 1

    // build the grid
    for (y <- 0 until intInput.length) {
      val row = intInput(y)
      for (x <- 0 until row.size) {
        grid(Loc(x, y)) = row(x)
      }
    }

    val gridKeys = grid.keys.filterNot(l => grid(l) == 9)

    pointsToCheck = ArrayBuffer(gridKeys.toList: _*)

    var basins = ArrayBuffer.empty[List[Loc]]


    while (!pointsToCheck.isEmpty) {
      println(s"points to check left: ${pointsToCheck.size}")
      val nextToCheck = pointsToCheck.head
      val allAdjacents = searchRecursively(nextToCheck) ++ List(nextToCheck)
      basins.append(allAdjacents)
      pointsToCheck = pointsToCheck
        .filterNot(l => allAdjacents.contains(l))
    }

    println(s"finished, basins: ${basins.length}")

    val res = basins.map(b => b.length)
      .sorted
      .reverse
      .take(3)
      .reduce((a, b) => a * b)

    println(s"res: $res")

  }


  private def searchRecursively(loc: Loc): List[Loc] = {

    val adjacent = getAdjacentLocs(loc, maxX, maxY)
      .filter(l => pointsToCheck.contains(l)) // remove adjacent locations already checked

    if (adjacent.isEmpty) {
      List(loc)
    } else {
      pointsToCheck = pointsToCheck.filterNot(p => adjacent.contains(p) || p == loc)
      (adjacent ++ adjacent.flatMap(a => searchRecursively(a))).distinct
    }
  }


  private def getAdjacentLocs(loc: Loc, maxX: Int, maxY: Int): List[Loc] = {
    var x = loc.x
    val y = loc.y

    val possibleAdjacents = List(Loc(x - 1, y), Loc(x + 1, y), Loc(x, y - 1), Loc(x, y + 1))
      .filter(l => l.x >= 0 && l.x <= maxX && l.y >= 0 && l.y <= maxY)

    possibleAdjacents
  }


  case class Loc(x: Int, y: Int)
}
