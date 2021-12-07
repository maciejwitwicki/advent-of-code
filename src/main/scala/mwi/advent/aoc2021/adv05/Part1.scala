package mwi.advent.aoc2021.adv05

import scala.collection.mutable

object Part1 {

  def solve(input: Array[String]): Unit = {

    val lines = parseLines(input)

    val grid = lines.filter(l => {
      l.isHorizontal || l.isVertical
    })
      .foldLeft(mutable.Map.empty[Point, Int])((acc, line) => {
        line.getAllPoints
          .foreach(p => {
            val prevValue = acc.getOrElseUpdate(p, 0)
            acc.put(p, prevValue +1)
          })
         acc
      })

    grid.foreach(el => println(el._1 + "- > " + el._2))
    val result = grid.values.count(e => e > 1)

    print(s"overlapping points count: $result")

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
      val (x1, x2) = sort(start.x, end.x)
      val (y1, y2) = sort(start.y, end.y)
      val list = (x1 to x2).flatMap(i => {
        (y1 to y2).map(j => Point(i, j))
      }).toList
      list
    }

    def isHorizontal: Boolean = {
      start.x == end.x
    }

    def isVertical: Boolean = {
      start.y == end.y
    }

    private def sort(a: Int, b: Int) = {
      if (a > b) (b, a)
      else (a, b)
    }
  }

  case class Grid(map: mutable.Map[Point, Int]) {

  }

}
