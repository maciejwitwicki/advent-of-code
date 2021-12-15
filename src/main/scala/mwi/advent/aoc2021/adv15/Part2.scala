package mwi.advent.aoc2021.adv15

import mwi.advent.util.Loc

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part2 {

  private val grid = mutable.HashMap.empty[Loc, Point]
  private var maxX, maxY, baseGridLength, baseGridHeight = 0
  val closedset = mutable.HashSet.empty[Loc]// ArrayBuffer.empty[Loc]
  val openset = mutable.HashSet.empty[Loc]// ArrayBuffer.empty
  val gScore = mutable.HashMap.empty[Loc, Double]
  val hScore = mutable.HashMap.empty[Loc, Double]
  val fScore = mutable.HashMap.empty[Loc, Double]
  val cameFrom = mutable.HashMap.empty[Loc, Loc]
  var iteration = 0;

  def solve(input: Array[String]): Unit = {

    parseGrid(input)
    printGrid()

    gScore(Loc(0, 0)) = 0
    fScore(Loc(0, 0)) = 0

    openset.add(Loc(0, 0))

    var finalPath = ArrayBuffer.empty[Loc]

    val start: BigInt = BigInt(System.currentTimeMillis()) / 1000

    while (openset.nonEmpty) {

      iteration += 1

      val curPos = getAdjacentWithLowestFscore(openset)

      if (iteration % 100000 == 0) {
        printProgress(reconstructPath(cameFrom, curPos))
      }

      if (curPos.x == maxX && curPos.y == maxY) {
        println("at the target")
        finalPath = reconstructPath(cameFrom, curPos)
        openset.clear();
      } else {

        openset.filterInPlace(el => el != curPos)

        closedset.add(curPos)

        val newAdjacents = getAdjacentLocs(curPos)

        newAdjacents.filterNot(a => closedset.contains(a))
          .foreach(neighbor => {
            val distBetweenXandY = grid(neighbor).risk
            val tentativeGscore: Double = gScore(curPos) + distBetweenXandY

            var tentativeIsBetter = false

            if (!openset.contains(neighbor)) {
              openset.add(neighbor)
              val heuristic = calculateHeuristicsFor(neighbor)
              hScore(neighbor) = heuristic
              tentativeIsBetter = true
            } else if (tentativeGscore < gScore(neighbor)) {
              tentativeIsBetter = true
            }

            if (tentativeIsBetter) {
              cameFrom(neighbor) = curPos
              gScore(neighbor) = tentativeGscore
              fScore(neighbor) = tentativeGscore + hScore(neighbor)
            }
          })

      }
    }

    val end: BigInt = BigInt(System.currentTimeMillis()) / 1000

    val elapsed: BigInt = end - start

    println(s"Finished")

    println(finalPath.mkString(" -> "))

    printProgress(finalPath)

    val riskSum = finalPath.map(l => grid(l).risk).sum
    println(s"risk sum $riskSum, took $elapsed s")
  }

  private def printProgress(path: ArrayBuffer[Loc]): Unit = {
    println("\u001b[2J")
    println(s"iteration: $iteration")
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

  private def getAdjacentWithLowestFscore(adjacents: mutable.HashSet[Loc]): Loc = {
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

    baseGridHeight = input.length
    baseGridLength = input(0).length

    for (y <- 0 until input.length) {
      for (x <- 0 until input(y).length) {
        val value = Integer.parseInt(s"${input(y)(x)}")
        grid.put(Loc(x, y), Point(value, false))
      }
    }

    maxY = input.length - 1
    maxX = input(0).length - 1

    val singleGridLength = maxX + 1
    val singleGridHeight = maxY + 1

    maxX = baseGridLength * 5 - 1
    maxY = baseGridHeight * 5 - 1

    // copy grid horizontally

    for (copyId <- 1 to 4) {
      val newX = singleGridLength * copyId
      val newY = 0

      for (y <- newY until newY + singleGridHeight) {

        for (x <- newX until newX + singleGridLength) {

          val baseLoc = Loc(x - singleGridLength, y)
          val prevValue = grid(baseLoc).risk
          var newValue = prevValue + 1
          if (newValue > 9) newValue = 1
          grid(Loc(x, y)) = Point(newValue, false)
        }
      }
    }

    // copy grid vertically

    for (copyId <- 1 to 4) {
      val newX = 0
      val newY = singleGridHeight * copyId

      for (y <- newY until newY + singleGridHeight) {

        for (x <- newX to maxX) {

          val baseLoc = Loc(x, y - singleGridHeight)
          val prevValue = grid(baseLoc).risk
          var newValue = prevValue + 1
          if (newValue > 9) newValue = 1
          grid(Loc(x, y)) = Point(newValue, false)
        }
      }
    }


  }

  val colors = List(Console.RED, Console.GREEN, Console.YELLOW, Console.RESET)
  var colorIndex = 0;

  private def printGrid() = {
    println("== grid ==")
    for (y <- 0 to maxY) {


      var line = ""

      if (y % baseGridHeight == 0) {
        line += getNextColor()
      }

      for (x <- 0 to maxX) {
        line += grid(Loc(x, y)).risk
      }
      println(line)
    }
  }

  private def getNextColor() = {
    colorIndex += 1
    colorIndex = colorIndex % colors.length
    colors(colorIndex)
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
