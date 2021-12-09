package mwi.advent.aoc2021.adv09

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 {

  def solve(input: Array[String]): Unit = {

    var grid: mutable.Map[Loc, Int] = mutable.HashMap()

    val intInput = input.map(line => {
      line.toArray.map(c => Integer.parseInt(s"$c"))
    })


    var maxX = intInput(0).length - 1
    var maxY = intInput.length - 1

    for (y <- 0 until intInput.length) {

      val row = intInput(y)

      for (x <- 0 until row.size) {

        grid(Loc(x, y)) = row(x)

      }

    }


    var lowPoints = ArrayBuffer.empty[Loc]


    grid.foreach( (loc, value) => {

      val x = loc.x // for debug
      val y = loc.y // for debug

      val adjValues = getAdjacentLocs(loc, maxX, maxY)
        .map(l => grid(l))

      val lowestAdjValue = adjValues.distinct.min

      if (value < lowestAdjValue) {
        lowPoints.append(loc)
      }

    })

    //println(lowPoints.mkString(","))

    val res = lowPoints.map(l => grid(l) + 1).sum
    println(s"result: $res")


  }

  private def getAdjacentLocs(loc: Loc, maxX: Int, maxY: Int): List[Loc] = {
    var x = loc.x
    val y = loc.y

    val possibleAdjacents = List( Loc(x-1, y), Loc(x+1, y), Loc(x, y - 1), Loc(x, y + 1))
      .filter(l => l.x >= 0 && l.x <= maxX && l.y >= 0 && l.y <= maxY)

    possibleAdjacents
  }




  case class Loc(x: Int, y: Int)
}
