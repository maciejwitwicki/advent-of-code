package aoc2020.adv03

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Part1 {

  def solve1(input: Array[String]) = {


    var cursor2 = 0
    var trees2 = 0
    var checkDown2 = true

    val slopeTrees = mutable.ArrayBuffer(0,0,0,0)

    val cursors = mutable.ArrayBuffer(0,0,0,0)

    for (line <- input) {
      val length = line.length

      for (i <- 0 until cursors.length) {
        val curPos = cursors(i)
        if (line.charAt(curPos) == '#') {
          val currentTreeCount = slopeTrees(i)
          slopeTrees.update(i, currentTreeCount + 1)
        }
        val newPos = moveCursor(i, curPos, length)
        cursors.update(i, newPos)
      }

      if (checkDown2) {
        if (line.charAt(cursor2) == '#') trees2 = trees2 + 1
        cursor2 = cursor2 + 1
        if (cursor2 >= length) cursor2 = cursor2 - length
      }


      checkDown2 = !checkDown2
    }

    println(s"Result $slopeTrees")
    println(s"Result 2 $trees2")
    val res = slopeTrees.map(_.longValue).foldRight(1L)(_ * _)

    println(s"Result final ${res * trees2.longValue()}")
  }

  private def moveCursor(index: Int, currentPos: Int, maxPos: Int) = {
    val cursorsOffset = Array(1,3,5,7)
    var nextPos = currentPos + cursorsOffset(index)
    if (nextPos >= maxPos) nextPos = nextPos - maxPos
    nextPos
  }

}


