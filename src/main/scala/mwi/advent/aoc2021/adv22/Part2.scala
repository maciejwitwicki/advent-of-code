package mwi.advent.aoc2021.adv22

import mwi.advent.aoc2021.adv19.impl.Parser
import mwi.advent.util.{AdventHelpers, Loc, Loc3d}

import java.math.BigInteger
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

object Part2 extends AdventHelpers {
  private implicit val ec: ExecutionContext = ExecutionContext.global

  private val grid = mutable.HashMap.empty[Loc3d, Boolean]

  private val enabledCubids = mutable.HashSet.empty[Cubid]

  def solve(input: Array[String]): Unit = {

    val instructions = parseInput(input)

    instructions.foreach(i => {
      executeInstruction(i)
      println(s" === instruction: $i === ")
      // grid.foreach(e => println(e._1))
    })

    // grid.foreach(e => println(e._1))

    val res = enabledCubids.size
    println(s"there are $res cubids to calculate")

    val totalVolume = enabledCubids.foldLeft(0L)((acc, cubid) => {
      acc + cubid.volume
    })

    enabledCubids.foreach(println)

    println(s"volume total: $totalVolume")

  }

  private def executeInstruction(instruction: Part2.Instruction) = {

    val currentCubid = instruction.cubid

    if (enabledCubids.isEmpty) {
      enabledCubids.add(currentCubid)
    } else {
      val collidingCubes = enabledCubids.filter(c => c.intersectsWith(currentCubid))

      println(s"colliding cubes size ${collidingCubes.size}")

      collidingCubes.foreach(c => {
        enabledCubids.remove(c)

        val remainsOfBiggerCubid = c.cutIntoPartsRemovingIntersectionWith(currentCubid)

        enabledCubids.addAll(remainsOfBiggerCubid)

      })

      if (instruction.enabled) {
        enabledCubids.add(currentCubid)
      }

    }

  }

  private def parseInput(input: Array[String]) = {
    input.map(line => {
      val on      = line(1) == 'n'
      val numbers = extractNumbers(line).map(_.toInt).toList

      Instruction(Cubid(numbers.head, numbers(1), numbers(2), numbers(3), numbers(4), numbers(5)), on)

    })
  }

  private[adv22] case class Cubid(minX: Int, maxX: Int, minY: Int, maxY: Int, minZ: Int, maxZ: Int) {
    def intersectsWith(other: Cubid): Boolean = {

      val disjointOnX = this.maxX < other.minX || this.minX > other.maxX
      val disjonitOnY = this.maxY < other.minY || this.minY > other.maxY
      val disjonitOnZ = this.maxZ < other.minZ || this.minZ > other.maxZ

      !disjointOnX && !disjonitOnY && !disjonitOnZ
    }

    def volume: Long = {
      (Math.abs(maxX - minX) + 1).longValue *
        (Math.abs(maxY - minY) + 1).longValue *
        (Math.abs(maxZ - minZ) + 1).longValue
    }

    def cutIntoPartsRemovingIntersectionWith(other: Cubid): Set[Cubid] = {

      val result = mutable.HashSet.empty[Cubid]

      // prepare split points

      val splitPointsX = ArrayBuffer(SplitPoint(this.minX, false))
      if (other.minX > this.minX) {
        splitPointsX.addOne(SplitPoint(other.minX, true))
      }
      if (other.maxX < this.maxX) {
        splitPointsX.addOne(SplitPoint(other.maxX, true))
      }
      splitPointsX.addOne(SplitPoint(this.maxX, false))

      val splitPointsY = ArrayBuffer(SplitPoint(this.minY, false))
      if (other.minY > this.minY) {
        splitPointsY.addOne(SplitPoint(other.minY, true))
      }
      if (other.maxY < this.maxY) {
        splitPointsY.addOne(SplitPoint(other.maxY, true))
      }
      splitPointsY.addOne(SplitPoint(this.maxY, false))

      val splitPointsZ = ArrayBuffer(SplitPoint(this.minZ, false))
      if (other.minZ > this.minZ) {
        splitPointsZ.addOne(SplitPoint(other.minZ, true))
      }
      if (other.maxZ < this.maxZ) {
        splitPointsZ.addOne(SplitPoint(other.maxZ, true))
      }

      splitPointsZ.addOne(SplitPoint(this.maxZ, false))

      // crate all new cubids

      val lastXindex = splitPointsX.size - 1
      val lastYindex = splitPointsY.size - 1
      val lastZindex = splitPointsZ.size - 1

      for (z <- 0 until lastZindex) {
        for (y <- 0 until lastYindex) {
          for (x <- 0 until lastXindex) {

            val minXsplit = splitPointsX(x)
            val maxXsplit = splitPointsX(x + 1)
            val minYsplit = splitPointsY(y)
            val maxYsplit = splitPointsY(y + 1)
            val minZsplit = splitPointsZ(z)
            val maxZsplit = splitPointsZ(z + 1)

            var minX = minXsplit.i
            var maxX = maxXsplit.i

            if (!minXsplit.fromOther && maxXsplit.fromOther) {
              maxX = maxXsplit.i - 1
            }

            if (minXsplit.fromOther && !maxXsplit.fromOther) {
              minX = minXsplit.i + 1
            }

            var minY = minYsplit.i
            var maxY = maxYsplit.i

            if (!minYsplit.fromOther && maxYsplit.fromOther) {
              maxY = maxYsplit.i - 1
            }

            if (minYsplit.fromOther && !maxYsplit.fromOther) {
              minY = minYsplit.i + 1
            }

            var minZ = minZsplit.i
            var maxZ = maxZsplit.i

            if (!minZsplit.fromOther && maxZsplit.fromOther) {
              maxZ = maxZsplit.i - 1
            }

            if (minZsplit.fromOther && !maxZsplit.fromOther) {
              minZ = minZsplit.i + 1
            }

            result.add(Cubid(minX, maxX, minY, maxY, minZ, maxZ))
          }
        }
      }

      result
        .filterNot(c => c.intersectsWith(other))
        .toSet

    }

  }

  private case class Instruction(cubid: Cubid, enabled: Boolean)
  private case class SplitPoint(i: Int, fromOther: Boolean)
}
