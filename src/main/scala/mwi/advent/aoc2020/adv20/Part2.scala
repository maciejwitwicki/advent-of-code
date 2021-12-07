package mwi.advent.aoc2020.adv20

import mwi.advent.aoc2020.adv20.Advent_20.s1

import scala.collection.mutable
import scala.collection.parallel.CollectionConverters._
import scala.io.Source

object Part2 {
  var tiles = Array.empty[Tile]
  val pattern = """^Tile (\d+):$""".r

  val dragonStr =
  """                  #
    |#    ##    ##    ###
    | #  #  #  #  #  #
    |""".stripMargin

  val dragon = Source.fromString(dragonStr).getLines().toArray

  val tilesById = mutable.HashMap.empty[String, Tile]


  def solve(input: Array[String]) = {

    parseTiles(input)

    tiles.foreach(tile => {
      println(s"-----> checking tile ${tile.id}")
      tiles.filter(t => t.id != tile.id).foreach(other => {
        val hasCcommonEdge = other.allCombinations.toList.par.exists(c => tile.allCombinations.toList.contains(c))
        if (hasCcommonEdge) {
          println(s"Tile ${tile.id} has a common edge with ${other.id}")
          val prev = tilesById.getOrElse(tile.id, tile)
          val prevNeighbours = prev.neighbors
          tilesById.put(tile.id, tile.copy(neighbors = prevNeighbours + other.id))
        }
      })
    })

    tilesById.values.toList.sortBy(_.neighbors.size).foreach(e => println(e.id, e.neighbors))

    println(s"multiplying only those with 2 neighbours")

    val corners = tilesById.values.toList.filter(z => z.neighbors.size == 2)
    corners.foreach(c => println(s"${c.id}: ${c.neighbors.mkString(", ")}"))

    val res = corners.map(c => BigDecimal(c.id)).foldLeft(BigDecimal(1))(_ * _)
    println(s"Result $res")

    // =========================================================================================================
    // === part 2 ====
    // =========================================================================================================

    val startingTile = corners(0).copy(loc = Loc(0, 0))
    tilesById.put(startingTile.id, startingTile)

    var remainingTiles = tilesById.values.filterNot(_.id == startingTile.id).toArray
    var usedTiles = Set(startingTile.id)

    var it = 0
    val to = remainingTiles.length
    while (it < to) {

      val (newTile, newRemainingTiles, neighborsToUse) = getOneThatWeAlreadyPositionedANeighbourOf(remainingTiles, usedTiles)
      remainingTiles = newRemainingTiles
      usedTiles = usedTiles + newTile.id

      val neighbor = tilesById(neighborsToUse.head)

      val relations = newTile.allRotationsAndFlips.map(newTileVariant => findRelation(neighbor, newTileVariant)).filter(relation => relation._1 != null)

      if (relations.length > 1) println(s"More than one rotation matches tile!")

      val (updatedNeighbor, updatedNewTile) = relations.head

      tilesById.put(updatedNeighbor.id, updatedNeighbor)
      tilesById.put(updatedNewTile.id, updatedNewTile)

      it = it + 1
    }


    println("Puzzle alignment:")
    printPuzzle(tilesById.values)

    val translatedTiles = tilesById.values.map(t => t.copy(initialDrawing = translateDrawing(t.initialDrawing, t.flippedBy, t.rotatedBy)))

    printDrawing(translatedTiles)

    val withoutBourders = removeBorders(translatedTiles)

    println("\nRemoved borders:")
    printDrawing(withoutBourders)

    val oneBigImage = toOneBigImage(withoutBourders)

    println("\nOne big image:")
    oneBigImage.foreach(println)

    println("\nLooking for the sea monster:")

    val flips: List[Char] = List('H', 'V', 0)
    val rots = List(0, 1, 2, 3)


    val images = flips.flatMap(f => rots.map(r => translateDrawing(oneBigImage, f, r)))

    var max = -10
    for (im <- images) {
      val find: Int = lookForTheDragon(im)
      println(s"Found dragons so far: $find")
      max = Math.max(max, find)
    }

    println(s"There are $max dragons")

    val allHashes = oneBigImage.mkString.count(ch => ch == '#')
    val dragonHashes = dragonStr.count(ch => ch == '#')
    val diff = allHashes - (dragonHashes * max)
    println(s" all hashes: $allHashes, dragon hashes $dragonHashes times $max is ${dragonHashes * max}, so diff is $diff")

  }

  private def lookForTheDragon(oneBigImage: Array[String]) = {
    var find = 0
    for (y <- 1 until oneBigImage.length - 1; x <- 0 until oneBigImage(0).length - dragon.head.length) {
      //println(s"y: $y x: $x")
      if (hasMainDragonLine(oneBigImage, y, x)) {
        if (hasSecond(oneBigImage, y, x)) {
          if (oneBigImage(y - 1)(x + 18) == '#') find = find + 1
        }
      }
    }
    find
  }

  private def hasMainDragonLine(image: Array[String], y: Int, x: Int) = {
    val firstLineLocations = List(0, 5, 6, 11, 12, 17, 18, 19 )
    firstLineLocations.forall(l => image(y)(x + l) == '#')
  }

  private def hasSecond(image: Array[String], y: Int, x: Int) = {
    val secondLineLocations = List(1, 4, 7, 10, 13, 16)
    secondLineLocations.forall(l => image(y + 1)(x + l) == '#')
  }

  private def toOneBigImage(tiles: Iterable[Tile]) = {
    val tileHeight = tiles.head.initialDrawing.length
    val tileWidth = tiles.head.initialDrawing.head.length

    val puzzle = tiles.map(e => e.loc -> e).toMap

    val rangeX = puzzle.keys.map(_.x).toList.sorted
    val minX = rangeX.head
    val maxX = rangeX.last

    val rangeY = puzzle.keys.map(_.y).toList.sorted
    val minY = rangeY.head
    val maxY = rangeY.last

    var y = maxY
    var result = Array.empty[String]
    while (y >= minY) {
     var tmpLines = Array.empty[String]
      for (l <- 0 until tileHeight) {
        var strTemp = ""
        for (x <- minX to maxX) {
          strTemp = strTemp + puzzle.get(Loc(x, y)).map(tile => tile.initialDrawing(l)).getOrElse(" " * tileWidth)
        }
       tmpLines = tmpLines :+ strTemp
      }
      result = result :++ tmpLines
      y = y - 1
    }
  result
  }

  private def removeBorders(tiles: Iterable[Tile]) = {
    val newTiles = tiles.map(t => {
      t.copy(initialDrawing = t.initialDrawing.drop(1).dropRight(1).map(line => line.drop(1).dropRight(1)))
    })
    newTiles
  }


  private def findRelation(base: Tile, other: Tile) = {
    if (base.top == other.bottom) {
      val otherLoc = base.loc.copy(y = base.loc.y + 1)
      base.copy(topNeighbor = Some(other.id)) -> other.copy(bottomNeighbor = Some(base.id), loc = otherLoc)
    }
    else if (base.right == other.left) {
      val otherLoc = base.loc.copy(x = base.loc.x + 1)
      base.copy(rightNeighbor = Some(other.id)) -> other.copy(leftNeighbor = Some(base.id), loc = otherLoc)
    }
    else if (base.bottom == other.top) {
      val otherLoc = base.loc.copy(y = base.loc.y - 1)
      base.copy(bottomNeighbor = Some(other.id)) -> other.copy(topNeighbor = Some(base.id), loc = otherLoc)
    }
    else if (base.left == other.right) {
      val otherLoc = base.loc.copy(x = base.loc.x - 1)
      base.copy(leftNeighbor = Some(other.id)) -> other.copy(rightNeighbor = Some(base.id), loc = otherLoc)
    }
    else (null, null)
  }

  private def translateDrawing(initial: Array[String], flip: Char, rotation: Int) = {
    val wordSize = initial(0).length

    val flipped = flip match {
      case 'H' => initial.reverse
      case 'V' => initial.map(_.reverse)
      case _ => initial
    }

    val rotated = rotation match {
      case 0 => flipped
      case 1 => {
        var newArray = Array.empty[String]
        for (x <- 0 until wordSize) {
          var newLine = ""
          for (y <- 0 until wordSize) {
            newLine = newLine + flipped(wordSize - 1 - y).charAt(x)
          }
          newArray = newArray :+ newLine
        }
        newArray
      }
      case 2 => flipped.map(_.reverse).reverse
      case 3 => {
        var newArray = Array.empty[String]
        for (x <- 0 until wordSize) {
          var newLine = ""
          for (y <- 0 until wordSize) {
            newLine = newLine + flipped(y).charAt(wordSize - x - 1)
          }
          newArray = newArray :+ newLine
        }
        newArray
      }
    }

    rotated

  }


  private def getOneThatWeAlreadyPositionedANeighbourOf(remainingTiles: Array[Tile], usedIds: Set[String]) = {
    val tilesWithNeighborsThatWereUsed = remainingTiles.filter(t => t.neighbors.intersect(usedIds).nonEmpty)
    val tilesSortedByNeighboursCount = tilesWithNeighborsThatWereUsed.sortBy(_.neighbors.size)
    val candidate = tilesSortedByNeighboursCount.head
    val neighboursToBaseWith = candidate.neighbors.intersect(usedIds)
    (candidate, remainingTiles.filterNot(t => t.id == candidate.id), neighboursToBaseWith.toArray)
  }

  private def printPuzzle(tiles: Iterable[Tile]) = {

    val puzzle = tiles.map(e => e.loc -> e).toMap

    val rangeX = puzzle.keys.map(_.x).toList.sorted
    val minX = rangeX.head
    val maxX = rangeX.last

    val rangeY = puzzle.keys.map(_.y).toList.sorted
    val minY = rangeY.head
    val maxY = rangeY.last

    var y = maxY
    while (y >= minY) {
      var strTemp = ""
      for (x <- minX to maxX) {
        strTemp = strTemp + puzzle.get(Loc(x, y)).map(tile => tile.id).getOrElse("    ") + " "
      }
      println(strTemp)
      y = y - 1
    }

  }

  private def printDrawing(tiles: Iterable[Tile]) = {
    val tileHeight = tiles.head.initialDrawing.length
    val tileWidth = tiles.head.initialDrawing.head.length

    val puzzle = tiles.map(e => e.loc -> e).toMap

    val rangeX = puzzle.keys.map(_.x).toList.sorted
    val minX = rangeX.head
    val maxX = rangeX.last

    val rangeY = puzzle.keys.map(_.y).toList.sorted
    val minY = rangeY.head
    val maxY = rangeY.last

    var y = maxY
    while (y >= minY) {
      var strTemp = ""
      for (l <- 0 until tileHeight) {

        for (x <- minX to maxX) {
          strTemp = strTemp + puzzle.get(Loc(x, y)).map(tile => tile.initialDrawing(l)).getOrElse(" " * tileWidth) + " "
        }
        strTemp = strTemp + "\n"
      }
      println(strTemp)

      y = y - 1
    }


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

    println(s"Loaded ${
      tiles.size
    } tiles")

  }

}

case class Tile(
                 id: String,
                 initialDrawing: Array[String],
                 top: String,
                 right: String,
                 bottom: String,
                 left: String,
                 neighbors: Set[String] = Set.empty[String],
                 rotatedBy: Int = 0,
                 flippedBy: Char = 0,
                 topNeighbor: Option[String] = None,
                 rightNeighbor: Option[String] = None,
                 bottomNeighbor: Option[String] = None,
                 leftNeighbor: Option[String] = None,
                 loc: Loc = Loc(-100, -100)
               ) {

  def allCombinations = Set(top, top.reverse, right, right.reverse, bottom, bottom.reverse, left, left.reverse).toArray

  def rotate(times: Int) = times match {
    case 0 => this.copy()
    case 1 => this.copy(top = this.left.reverse, right = this.top, bottom = this.right.reverse, left = this.bottom, rotatedBy = 1)
    case 2 => this.copy(top = this.bottom.reverse, right = this.left.reverse, bottom = this.top.reverse, left = this.right.reverse, rotatedBy = 2)
    case 3 => this.copy(top = this.right, right = this.bottom.reverse, bottom = this.left, left = this.top.reverse, rotatedBy = 3)
    case _ => throw new RuntimeException("WTF !?")
  }

  def flip(direction: Char) = direction match {
    case 'H' => this.copy(top = this.bottom, right = this.right.reverse, bottom = this.top, left = this.left.reverse, flippedBy = 'H')
    case 'V' => this.copy(top = this.top.reverse, right = this.left, bottom = this.bottom.reverse, left = this.right, flippedBy = 'V')
    case _ => throw new RuntimeException("WTF !?")
  }

  def allRotationsAndFlips = {

    val allFlips = Array(this, this.flip('H'), this.flip('V')).par

    val allRotationsAndFlips = allFlips.flatMap(t => {
      (0 to 3).par.map(t.rotate)
    })

    // some rotations do the same result as some flips, so 12 variations are reduced to 8
    val tilesBySingleLine = allRotationsAndFlips.toArray.map(t => t.toSingleLine() -> t).sortBy(_._1)

    tilesBySingleLine.toMap.values.toArray
  }

  def toSingleLine() = this.top + this.right + this.bottom + this.left
}

case class Loc(x: Int, y: Int)
