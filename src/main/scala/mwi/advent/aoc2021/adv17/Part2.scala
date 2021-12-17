
package mwi.advent.aoc2021.adv17

import mwi.advent.util.Loc

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part2 {

  private var target = Target(0, 0, 0, 0)
  private final val Margin = 10

  def solve(input: Array[String]): Unit = {

    parseTarget(input)

    println(target)

    printGrid()


    var foundVelocities = mutable.HashSet.empty[Loc]

    val initialXvelocity = 0
    val maxX = initialXvelocity + 100
    val initialYvelocity = -10
    val maxY = initialYvelocity + 100

    var xVelocity = initialXvelocity
    for (f <- 0 until maxX) {

      var yVelocity = initialYvelocity

      for (u <- 0 until maxY) {

        val hit = fire(xVelocity, yVelocity)

        if (hit) {
          foundVelocities.add(Loc(xVelocity, yVelocity))
        }

        yVelocity += 1
      }

      xVelocity += 1
    }
    println(s"hits : ${foundVelocities.size}: ${foundVelocities.mkString(", ")}")
  }

  private def fire(initialXVelocity: Int, initialYVelocity: Int): Boolean = {

    val trajectory = mutable.HashSet.empty[Loc]

    var x, y = 0
    var xVel = initialXVelocity
    var yVel = initialYVelocity

    var found = false

    val failsafe = 100000
    var it = 0
    while (it < failsafe && (x < target.maxX + Margin && y > target.minY - Margin)) {

      it += 1

      x += xVel
      y += yVel

      var drag = 0
      if (xVel > 0) drag = -1
      if (xVel < 0) drag = 1

      xVel += drag

      yVel -= 1

      if (inTheTarget(x, y)) {
        found = true
      }

      trajectory.add(Loc(x, y))
    }

    if (found) printGrid(trajectory, initialXVelocity, initialYVelocity)

    found

  }

  private def printGrid(path: mutable.HashSet[Loc] = mutable.HashSet.empty, xVelocity: Int = 0, yVelocity: Int = 0) = {

    println(s"x vel: $xVelocity, y vel: $yVelocity === ==== ==== ")

    val translatedY = translate(target.minY, target.maxY)

    val t = target

    for (y <- translatedY) {
      for (x <- 0 until target.maxX + Margin) {


        if (x == 0 && y == 0) {
          print(Console.RED + "S")
        } else if (path.contains(Loc(x, y))) {
          print(Console.RED + "#")
        } else {
          val printT = inTheTarget(x, y)

          if (printT)
            print(Console.YELLOW + "T")
          else
            print(Console.RESET + ".")
        }

      }
      println
    }
  }

  private def inTheTarget(x: Int, y: Int): Boolean = {
    (x >= target.minX && x <= target.maxX) && (y >= target.minY && y <= target.maxY)
  }


  private def translate(min: Int, max: Int) = {
    var i = Margin
    val result = ArrayBuffer.empty[Int]
    while (i > min - Margin) {
      result.append(i)
      i -= 1
    }
    result
  }

  private def parseTarget(input: Array[String]) = {
    val line = input(0).drop(13)
    val indexOf = line.indexOf(", ")
    val (xPart, yPart) = line.splitAt(indexOf)

    val xsplit = xPart.drop(2).split("\\.\\.")
    val ysplit = yPart.drop(4).split("\\.\\.")

    val minX = Integer.parseInt(xsplit(0))
    val maxX = Integer.parseInt(xsplit(1))
    val minY = Integer.parseInt(ysplit(0))
    val maxY = Integer.parseInt(ysplit(1))

    val realMinY = Math.min(minY, maxY)
    val realMaxY = Math.max(minY, maxY)

    target = Target(minX, maxX, realMinY, realMaxY)

  }

  private case class Target(minX: Int, maxX: Int, minY: Int, maxY: Int)

}
