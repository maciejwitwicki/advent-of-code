package mwi.advent.aoc2020.adv24

import java.lang.System.currentTimeMillis
import scala.collection.mutable

object Part2 {


  case class Loc(x: Int, y: Int)

  var tiles = mutable.HashMap.empty[Loc, Boolean]

  for (y <- -100 to 100; x <- - 100 to 100) {
    tiles.put(Loc(x, y), true)
  }


  def solve(input: Array[String]) = {

    var lineCount = 0
    for (line <- input) {

      var i = 0
      var x: Int = 0
      var y: Int = 0

      while (i < line.length) {

        val letter = line(i)
        val direction = letter match {
          case 's' => {
            val sx = "s" + line(i + 1)
            i = i + 1
            sx
          }
          case 'n' => {
            val nx = "n" + line(i + 1)
            i = i + 1
            nx
          }
          case x => x.toString
        }

        val dx: Int = if (y % 2 == 0) {
          //println("y modulo 2 is 0, sw will decrease x, se not")
          1
        } else 0

        direction match {
          case "e" => x = x + 1
          case "w" => x = x - 1
          case "se" => y = y + 1; x = x + dx
          case "sw" => y = y + 1; x = x - 1 + dx
          case "ne" => y = y - 1; x = x + dx
          case "nw" => y = y - 1; x = x - 1 + dx
        }

        i = i + 1
      }
      val isWhite = tiles.getOrElse(Loc(x, y), true) // true means white
      val newColor = !isWhite // flip the color
      tiles.put(Loc(x, y), newColor)
      lineCount = lineCount + 1
      println(s"instruction $lineCount ends on x: $x y: $y with color (white = true, black = false) $newColor")
    }

    val res = tiles.values.count(p => !p)
    println(s"There are $res of black tiles")


    var day = 0
    while (day < 100) {
      val newTiles = mutable.HashMap.empty[Loc, Boolean]

      tiles.foreach(t => {
        val loc = t._1
        val blackNeighbours = getAdjacentBlackCountFor(loc)

        val currentWhite = t._2

        if (!currentWhite && (blackNeighbours == 0 || blackNeighbours > 2)) newTiles.put(loc, true)
        else if (currentWhite && blackNeighbours == 2) newTiles.put(loc, false)
        else newTiles.put(loc, currentWhite)

      })

      tiles = newTiles

      day = day + 1
      val res = tiles.values.count(p => !p)
      println(s"There are $res of black tiles after day $day")
    }


  }

  private def getAdjacentBlackCountFor(loc: Loc) = {

    import loc._

    val dx: Int = if (y % 2 == 0) {
      //println("y modulo 2 is 0, sw will decrease x, se not")
      1
    } else 0

    val neighbours = Array(
      tiles.getOrElse(Loc(x - 1, y), true),
      tiles.getOrElse(Loc(x + 1, y), true),
      tiles.getOrElse(Loc(x - 1 + dx, y - 1), true),
      tiles.getOrElse(Loc(x + dx, y - 1), true),
      tiles.getOrElse(Loc(x - 1 + dx, y + 1), true),
      tiles.getOrElse(Loc(x + dx, y + 1), true),
    )

    neighbours.count(p => !p)
  }

}
