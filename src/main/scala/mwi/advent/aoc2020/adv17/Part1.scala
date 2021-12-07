package mwi.advent.aoc2020.adv17

import scala.collection.mutable

object Part1 {


  var grid = mutable.HashMap.empty[Loc, Point]


  // game of live in 3 dimensions
  def solve(input: Array[String]) = {

    val min = -20
    val max = 20


    println("creating the grid")
    for (z <- min to max; y <- min to max; x <- min to max) {
      val loc = Loc(x, y, z)
      grid.put(loc, Point(loc))
    }


    val z = 0
    var y = 0
    var x = 0

    println("updating the grid with input data")
    for (line <- input) {
      x = 0
      for (l <- line.toCharArray) {
        if (l == '#') {
          val loc = Loc(x, y, z)
          val prev = grid.get(loc).get
          grid.update(loc, prev.copy(active = true))
        }

        x = x + 1
      }
      y = y + 1
    }

    println("-> setting each cube neighbor")
    for (p <- grid.values) {

      for (nz <- -1 to 1; ny <- -1 to 1; nx <- -1 to 1) {

        val nbLoc = p.loc.translate(nx, ny, nz)
        if (nbLoc != p.loc) {
          grid.get(nbLoc)
            .map(nb => p.addNeighbor(nb))
        }
      }
    }


    printGrid(grid)

    var cycle = 1

    while (cycle <= 6) {
      println("-> resetting the active state")

      val newGrid = mutable.HashMap.empty[Loc, Point]

      grid.foreach(entry => {
        val point = entry._2
        val activeNeighbours = point.neighbours.count(_.active)
        if (point.active) {
          if (activeNeighbours == 2 || activeNeighbours == 3)
            newGrid.put(point.loc, point.copy(active = true))
          else
            newGrid.put(point.loc, point.copy(active = false))
        } else {
          if (activeNeighbours == 3)
            newGrid.put(point.loc, point.copy(active = true))
          else
            newGrid.put(point.loc, point.copy(active = false))
        }
      })
      printGrid(newGrid)

      println(s"-> setting each cube neighbor in NEW GRID in cycle $cycle")
      for (p <- newGrid.values) {

        for (nz <- -1 to 1; ny <- -1 to 1; nx <- -1 to 1) {

          val nbLoc = p.loc.translate(nx, ny, nz)
          if (nbLoc != p.loc) {
            newGrid.get(nbLoc)
              .map(nb => p.addNeighbor(nb))
          }
        }
      }
      cycle = cycle + 1
      grid = newGrid
    }

  }

  private def printGrid(g: mutable.HashMap[Loc, Point]) = {
    val active = g.values.filter(_.active)
      active.foreach(println)
    println(s"-> there were active cubes ${active.size}")
  }

  case class Loc(x: Int, y: Int, z: Int) {
    def translate(tx: Int, ty: Int, tz: Int) = Loc(x + tx, y + ty, z + tz)
  }

  case class Point(loc: Loc, active: Boolean = false) {

    var neighbours: Set[Point] = Set.empty

    def isNeighborOf(other: Point) = {
      val oloc = other.loc
      oloc != loc && distance(oloc.x, loc.x) <= 1 && distance(oloc.y, loc.y) <= 1 && distance(oloc.z, loc.z) <= 1
    }

    def addNeighbor(p: Point) = neighbours = neighbours + p

  }

  private def distance(i: Int, j: Int) = Math.abs(i - j)
}
