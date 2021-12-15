package mwi.advent.aoc2021.adv15

import mwi.advent.util.Loc

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 {

  private val grid = mutable.HashMap.empty[Loc, Point]
  private var maxX, maxY = 0
  val closedset = ArrayBuffer.empty[Loc]
  val openset: ArrayBuffer[Loc] = ArrayBuffer.empty //ArrayBuffer(getAdjacentLocs(Loc(0, 0)): _*) :+ Loc(0, 0)
  val gScore = mutable.HashMap.empty[Loc, Double]
  val hScore = mutable.HashMap.empty[Loc, Double]
  val fScore = mutable.HashMap.empty[Loc, Double]
  val cameFrom = mutable.HashMap.empty[Loc, Loc]

  def solve(input: Array[String]): Unit = {

    parseGrid(input)
    printGrid()

    var iteration = 0;


    gScore(Loc(0, 0)) = 0
    fScore(Loc(0, 0)) = 0

    openset.append(Loc(0, 0))

    var finalPath = ArrayBuffer.empty[Loc]

    while (openset.nonEmpty) {

      iteration += 1

      val curPos = getAdjacentWithLowestFscore(openset)

      if (iteration % 1 == 0) {
        printProgress(reconstructPath(cameFrom, curPos))
      }

      if (curPos.x == maxX && curPos.y == maxY) {
        println("at the target")
        finalPath = reconstructPath(cameFrom, curPos)
        openset.clear();
      } else {

        openset.filterInPlace(el => el != curPos)

        closedset.append(curPos)

        val newAdjacents = getAdjacentLocs(curPos)

        newAdjacents.filterNot(a => closedset.contains(a))
          .foreach(neighbor => {
            val distBetweenXandY = grid(neighbor).risk
            val tentativeGscore: Double = gScore(curPos) + distBetweenXandY

            var tentativeIsBetter = false

            if (!openset.contains(neighbor)) {
              openset.append(neighbor)
              val heuristic = calculateHeuristicsFor(neighbor)
              hScore(neighbor) = heuristic
              tentativeIsBetter = true
            } else if (tentativeGscore < gScore(neighbor)) {
              tentativeIsBetter = true
            }

            if (tentativeIsBetter) {
              cameFrom(neighbor) = curPos
              gScore(neighbor) = tentativeGscore
              fScore(neighbor) = gScore(neighbor) + hScore(neighbor)
            }
          })

      }
    }

    println(s"Finished")

    println(finalPath.mkString(" -> "))

    val riskSum = finalPath.map(l => grid(l).risk).sum
    println(s"risk sum $riskSum")
  }

  private def printProgress(path: ArrayBuffer[Loc]): Unit = {
    println("\u001b[2J")
    for (y <- 0 to maxY) {
      for (x <- 0 to maxX) {
        val risk = grid(Loc(x, y)).risk
        if (path.contains(Loc(x, y))) {
          print(Console.YELLOW + risk.toString)
        } else if (closedset.contains(Loc(x, y))) {
          print (Console.GREEN + risk.toString)
        } else {
          print(Console.RESET + risk.toString)
        }
      }
      println(Console.RESET)
    }
  }

  private def getAdjacentWithLowestFscore(adjacents: ArrayBuffer[Loc]): Loc = {
    val value1 = fScore.filter(el => adjacents.contains(el._1))
    val locWithMinFscore = value1.toList.minBy(el => el._2)
    locWithMinFscore._1
  }

  private def calculateHeuristicsFor(pos: Loc): Double = {
    val xDist = maxX - pos.x
    val yDist = maxY - pos.y

    Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2))
  }

  private def reconstructPath(cameFrom: mutable.HashMap[Loc, Loc], currentNode: Loc): ArrayBuffer[Loc] = {
    if (cameFrom.contains(currentNode)) {
      val prevPath = reconstructPath(cameFrom, cameFrom(currentNode))
      prevPath.append(currentNode)
    } else {
      ArrayBuffer.empty
    }
  }

  private def parseGrid(input: Array[String]) = {

    maxY = input.length - 1
    for (y <- 0 until input.length) {
      maxX = input(y).length - 1
      for (x <- 0 until input(y).length) {
        val value = Integer.parseInt(s"${input(y)(x)}")
        grid.put(Loc(x, y), Point(value, false))
      }
    }

  }

  private def printGrid() = {
    println("== grid ==")
    for (y <- 0 to maxY) {

      var line = ""
      for (x <- 0 to maxX) {
        line += grid(Loc(x, y)).risk
      }
      println(line)
    }
  }

  private def getAdjacentLocs(loc: Loc): List[Loc] = {
    val x = loc.x
    val y = loc.y

    val possibleAdjacents = List(Loc(x - 1, y), Loc(x + 1, y), Loc(x, y - 1), Loc(x, y + 1))
      .filter(l => l.x >= 0 && l.x <= maxX && l.y >= 0 && l.y <= maxY)

    possibleAdjacents
  }

  private case class Point(risk: Int, visited: Boolean)
}
