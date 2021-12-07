package mwi.advent.aoc2021.adv05

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part2 {

  def solve(input: Array[String]): Unit = {

    val lines = parseLines(input)

    val grid = lines.filter(l => {
      l.isHorizontal || l.isVertical || l.isDiagonal
    })
      .foldLeft(mutable.Map.empty[Point, Int])((acc, line) => {
        line.getAllPoints
          .foreach(p => {
            val prevValue = acc.getOrElseUpdate(p, 0)
            acc.put(p, prevValue +1)
          })
         acc
      })


    grid
      .toList
      .sortBy(el => el._1.x)
      .foreach(el => println(s"${el._1} - > ${el._2}"))
    val result = grid.values.count(e => e > 1)

    println(s"overlapping points count: $result")

  }

  private def parseLines(input: Array[String]): Array[Line] = {
    input.map(l => {
      val line = l.split(" -> ")
      assert(line.length == 2)
      val pairOfPoints = line.map(coords => {
        val points = coords.split(",")
          .map(n => Integer.parseInt(n))
        Point(points(0), points(1))
      }
      )
      Line(pairOfPoints(0), pairOfPoints(1))
    })
  }

  case class Point(x: Int, y: Int)

  case class Line(start: Point, end: Point) {

    def getAllPoints: List[Point] = {

      val xes = createRange(start.x, end.x)
      val yes = createRange(start.y, end.y)

      val result = ArrayBuffer.empty[Point]
      var i,j = 0
      var xDone, yDone = false
      while (!(xDone && yDone )) {
        val x = xes(i)
        val y = yes(j)
        result.append(Point(x, y))
        if (i < xes.length - 1) i+=1 else xDone = true
        if (j < yes.length - 1) j+=1 else yDone = true
      }

      result.toList
    }

    def isHorizontal: Boolean = {
      start.x == end.x
    }

    def isVertical: Boolean = {
      start.y == end.y
    }

    def isDiagonal: Boolean = {
      val diffX = start.x - end.x
      val diffY = start.y - end.y
      Math.abs(diffX) == Math.abs(diffY)
    }

    private def createRange(start: Int, end: Int): Range = {
      if (start > end) {
        (end to start).reverse
      } else start to end
    }
  }

  case class Grid(map: mutable.Map[Point, Int]) {

  }

}
