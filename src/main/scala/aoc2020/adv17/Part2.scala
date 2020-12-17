package aoc2020.adv17

import scala.collection.mutable

object Part2 {

  var grid = mutable.HashMap.empty[Loc, Point]


  // game of live in 4 dimensions

  // could be optimalized by starting from lowest min and max and have it set by the each dimension individually, spreading out the calculation complexity only as needed
  def solve(input: Array[String]) = {

    val min = -12
    val max = 12


    println("creating the grid")
    for (w <- min to max; z <- min to max; y <- min to max; x <- min to max) {
      val loc = Loc(x, y, z, w)
      grid.put(loc, Point(loc))
    }


    val w = 0
    val z = 0
    var y = 0
    var x = 0

    println("updating the grid with input data")
    for (line <- input) {
      x = 0
      for (l <- line.toCharArray) {
        if (l == '#') {
          val loc = Loc(x, y, z, w)
          val prev = grid.get(loc).get
          grid.update(loc, prev.copy(active = true))
        }

        x = x + 1
      }
      y = y + 1
    }

    println("-> setting each cube neighbor")
    for (p <- grid.values) {

      for (nw <- -1 to 1; nz <- -1 to 1; ny <- -1 to 1; nx <- -1 to 1) {

        val nbLoc = p.loc.translate(nx, ny, nz, nw)
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

        for (nw <- -1 to 1; nz <- -1 to 1; ny <- -1 to 1; nx <- -1 to 1) {

          val nbLoc = p.loc.translate(nx, ny, nz, nw)
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

  case class Loc(x: Int, y: Int, z: Int, w: Int) {
    def translate(tx: Int, ty: Int, tz: Int, tw: Int) = Loc(x + tx, y + ty, z + tz, w + tw)
  }

  case class Point(loc: Loc, active: Boolean = false) {

    var neighbours: Set[Point] = Set.empty

    def addNeighbor(p: Point) = neighbours = neighbours + p

  }
}

