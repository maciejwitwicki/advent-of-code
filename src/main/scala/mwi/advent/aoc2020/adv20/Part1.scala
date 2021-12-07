package mwi.advent.aoc2020.adv20

import scala.collection.mutable
import scala.collection.parallel.CollectionConverters._

object Part1 {

  var tiles = Array.empty[Tile]
  val pattern = """^Tile (\d+):$""".r

  val map = mutable.HashMap.empty[String, Tile]

  def solve(input: Array[String]) = {

    parseTiles(input)

    val tilesPar = tiles.par

    tiles.foreach(tile => {
      println(s"-----> checking tile ${tile.id}")
      tiles.filter(t => t.id != tile.id).foreach(other => {
        val hasCcommonEdge = other.allCombinations.toList.par.exists(c => tile.allCombinations.toList.contains(c))
        if (hasCcommonEdge) {
          println(s"Tile ${tile.id} has a common edge with ${other.id}")
          val prev = map.getOrElse(tile.id, tile)
          val prevNeighbours = prev.neighbors
          map.put(tile.id, tile.copy(neighbors = prevNeighbours + other.id))
        }
      })
    })

    map.values.toList.sortBy(_.neighbors.size).foreach(e => println(s"${e.id}, ${e.neighbors}"))

    println(s"multiplying only those with 2 neighbours")

    val corners = map.values.toList.filter(z => z.neighbors.size == 2)
    corners.foreach(c => println(s"${c.id}: ${c.neighbors.mkString(", ")}"))

    val res = corners.map(c => BigDecimal(c.id)).foldLeft(BigDecimal(1))(_ * _)
    println(s"Result $res")
  }

  private def parseTiles(input: Array[String]) = {

    var it = 0
    while (it < input.length) {

      // read id
      val id = pattern.findFirstMatchIn(input(it)) match {
        case Some(i) => i.group(1)
        case _ => throw new IllegalArgumentException("regex not found")
      }

      it = it + 1

      // read drawing

      var right = ""
      var left = ""

      var drawing = Array.empty[String]
      while (it < input.length && input(it).nonEmpty) {
        val line = input(it)
        drawing = drawing :+ line
        right = right + line.last
        left = left + line.head
        it = it + 1
      }

      it = it + 1

      val top = drawing.head
      val bottom = drawing.last

      tiles = tiles :+ Tile(id, drawing, top, right, bottom, left)
    }

    println(s"Loaded ${tiles.size} tiles")

  }

  case class Tile(id: String, initialDrawing: Array[String], top: String, right: String, bottom: String, left: String, neighbors: Set[String] = Set.empty[String]) {
    def allCombinations = Array(top, top.reverse, right, right.reverse, bottom, bottom.reverse, left, left.reverse)
  }

}
